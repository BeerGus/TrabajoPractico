package dev.danvega.h2demo.controller;

import dev.danvega.h2demo.model.Reserva;
import dev.danvega.h2demo.repository.ReservaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;
import dev.danvega.h2demo.model.Persona;
import dev.danvega.h2demo.repository.PersonaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*; // para @GetMapping, @PostMapping, etc.
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private static final String PYTHON_PREDICTOR_URL = "http://localhost:5000/api/predict";

    private final ReservaRepository reservaRepository;
    private final WebClient webClient;
    private final PersonaRepository personaRepository; // 👈 NUEVO




    // Inyección de dependencias (WebClient para Python, Repository para la DB)
    public ReservaController(ReservaRepository reservaRepository, WebClient.Builder webClientBuilder, PersonaRepository personaRepository) {
        this.reservaRepository = reservaRepository;
        this.webClient = webClientBuilder.baseUrl(PYTHON_PREDICTOR_URL).build();
        this.personaRepository = personaRepository;
    }
    // DTO para crear reserva desde el frontend
    public static class CrearReservaRequest {
        public Integer idSala;
        public Integer idArticulo; // puede ser null
        public String fechaHoraInicio; // formato ISO: "2025-09-11T10:00:00"
        public String fechaHoraFin;
    }

    // Método que llama a la API de Python para la predicción
    @GetMapping("/prediccion")
    public Mono<String> obtenerPrediccion() {
        // 1. Obtener el historial de reservas de la DB
        List<Reserva> historial = reservaRepository.findAll();

        // 2. Mapear el historial a la estructura que Python espera (duración en horas)
        List<Object> datosParaPython = historial.stream().map(reserva -> {
            // Calcula la duración en horas entre el inicio y el fin de la reserva
            // Nota: Este cálculo requiere que las fechas de las reservas no sean nulas.
            long duracionHoras = ChronoUnit.HOURS.between(reserva.getFechaHoraInicio(), reserva.getFechaHoraFin());

            // Creamos un objeto anónimo con los campos que Python espera
            return new Object() {
                public Integer id_sala = reserva.getIdSala();
                public Integer id_articulo = reserva.getIdArticulo();
                public float duracion_horas = (float) duracionHoras;
            };
        }).collect(Collectors.toList());

        // 3. Realizar la llamada POST al microservicio de Python
        return webClient.post()
                .body(Mono.just(datosParaPython), List.class) // Enviar la lista de datos históricos
                .retrieve()
                .bodyToMono(String.class); // Recibir la predicción de Python
    }

    // 1) Listar reservas ACTIVAS de una sala
    @GetMapping("/sala/{idSala}/activas")
    public List<Reserva> reservasActivasDeSala(@PathVariable Integer idSala) {
        LocalDateTime ahora = LocalDateTime.now();
        return reservaRepository.findReservasActivasPorSala(idSala, ahora);
    }

    // 2) Contar reservas activas de una sala (para la tabla de salas)
    @GetMapping("/sala/{idSala}/activas/count")
    public Map<String, Long> contarReservasActivasDeSala(@PathVariable Integer idSala) {
        LocalDateTime ahora = LocalDateTime.now();
        long cantidad = reservaRepository.countReservasActivasPorSala(idSala, ahora);
        return Map.of("cantidad", cantidad);
    }
    // 3) Crear reserva nueva para una sala
    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody CrearReservaRequest req,
                                          Authentication auth) {
        // Validaciones básicas del body
        if (req.idSala == null || req.fechaHoraInicio == null || req.fechaHoraFin == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Faltan datos obligatorios para la reserva.");
        }

        LocalDateTime inicio;
        LocalDateTime fin;

        try {
            inicio = LocalDateTime.parse(req.fechaHoraInicio);
            fin = LocalDateTime.parse(req.fechaHoraFin);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Formato de fecha/hora inválido. Usá ISO-8601, ej: 2025-09-11T10:00:00");
        }

        if (!fin.isAfter(inicio)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("La fecha/hora de fin debe ser posterior a la de inicio.");
        }

        // Buscar el usuario logueado
        String emailActual = auth.getName(); // Spring Security usa el email como username
        Optional<Persona> personaOpt = personaRepository.findByEmail(emailActual);
        if (personaOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("No se encontró el usuario logueado.");
        }

        Persona persona = personaOpt.get();

        // Validar que no haya reservas solapadas
        List<Reserva> solapadas = reservaRepository.findSolapadas(req.idSala, inicio, fin);
        if (!solapadas.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("La sala ya está reservada en ese horario.");
        }

        // Crear y guardar la reserva
        Reserva r = new Reserva();
        r.setIdSala(req.idSala);
        r.setIdArticulo(req.idArticulo);
        r.setIdPersona(persona.getId());
        r.setFechaHoraInicio(inicio);
        r.setFechaHoraFin(fin);
        r.setEstadoSala("ACTIVA");

        Reserva guardada = reservaRepository.save(r);

        return ResponseEntity.ok(guardada);
    }

    // 2.6) Cancelar una reserva (solo dueño o ADMIN)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarReserva(@PathVariable Integer id,
                                             Authentication auth) {

        Optional<Reserva> reservaOpt = reservaRepository.findById(id);
        if (reservaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada.");
        }
        Reserva reserva = reservaOpt.get();

        String emailActual = auth.getName();
        Optional<Persona> personaOpt = personaRepository.findByEmail(emailActual);
        if (personaOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("No se encontró el usuario logueado.");
        }
        Persona persona = personaOpt.get();

        boolean esDueno = reserva.getIdPersona().equals(persona.getId());
        boolean esAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!esDueno && !esAdmin) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("No tenés permisos para cancelar esta reserva.");
        }

        // Marcar como cancelada
        reserva.setEstadoSala("CANCELADA");
        reservaRepository.save(reserva);

        return ResponseEntity.ok(reserva);
    }


    // Método de soporte: Listar todas las reservas (para verificación)
    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }



}