/*
package dev.danvega.h2demo.controller;

import dev.danvega.h2demo.model.Reserva;
import dev.danvega.h2demo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservas")
public class PrediccionController {

    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping("/verprediccion")
    public String obtenerPrediccion(Model model) {

        // 1️⃣ Traer todas las reservas de la base
        List<Reserva> reservas = reservaRepository.findAll();

        if (reservas.isEmpty()) {
            model.addAttribute("mensaje", "⚠️ No hay reservas registradas para analizar la demanda.");
            model.addAttribute("status", "SIN DATOS");
            return "prediccion";
        }

        // 2️⃣ Calcular duración (en horas) de cada reserva real
        List<Map<String, Object>> historial = reservas.stream()
                .map(r -> {
                    double duracionHoras = 0.0;
                    try {
                        if (r.getFechaHoraInicio() != null && r.getFechaHoraFin() != null) {
                            duracionHoras = Duration.between(r.getFechaHoraInicio(), r.getFechaHoraFin()).toMinutes() / 60.0;
                        }
                    } catch (Exception e) {
                        duracionHoras = 0.0;
                    }

                    Map<String, Object> fila = new HashMap<>();
                    fila.put("id_sala", r.getIdSala());
                    fila.put("id_articulo", r.getIdArticulo());
                    fila.put("duracion_horas", duracionHoras);
                    return fila;
                })
                .collect(Collectors.toList());

        // 3️⃣ Enviar datos reales al microservicio Python
        String url = "http://localhost:5000/api/predict";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(historial, headers);

        Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);

        // 4️⃣ Pasar resultados a la vista
        model.addAttribute("status", response.get("status"));
        model.addAttribute("historica", response.get("demanda_total_historica"));
        model.addAttribute("prediccion", response.get("demanda_proyectada_horas"));
        model.addAttribute("mensaje", response.get("mensaje"));
        model.addAttribute("grafico", response.get("grafico_base64"));

        return "prediccion";
    }
}
*/
package dev.danvega.h2demo.controller;

import dev.danvega.h2demo.model.Reserva;
import dev.danvega.h2demo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservas")
public class PrediccionController {

    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping("/verprediccion")
    public String obtenerPrediccion(Model model) {

        // 1️⃣ Traer todas las reservas de la base
        List<Reserva> reservas = reservaRepository.findAll();

        if (reservas.isEmpty()) {
            model.addAttribute("mensaje", "⚠️ No hay reservas registradas para analizar la demanda.");
            model.addAttribute("status", "SIN DATOS");
            return "prediccion";
        }

        // 2️⃣ Calcular duración (en horas) de cada reserva real
        List<Map<String, Object>> historial = reservas.stream()
                .map(r -> {
                    double duracionHoras = 0.0;
                    try {
                        if (r.getFechaHoraInicio() != null && r.getFechaHoraFin() != null) {
                            duracionHoras = Duration.between(r.getFechaHoraInicio(), r.getFechaHoraFin()).toMinutes() / 60.0;
                        }
                    } catch (Exception e) {
                        duracionHoras = 0.0;
                    }

                    Map<String, Object> fila = new HashMap<>();
                    fila.put("id_sala", r.getIdSala());
                    fila.put("id_articulo", r.getIdArticulo());
                    fila.put("duracion_horas", duracionHoras);
                    return fila;
                })
                .collect(Collectors.toList());

        // 3️⃣ Enviar datos reales al microservicio Python
        String url = "http://localhost:5000/api/predict";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(historial, headers);

        Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);

        // 4️⃣ Pasar resultados a la vista
        model.addAttribute("status", response.get("status"));
        model.addAttribute("historica", response.get("demanda_total_historica"));
        model.addAttribute("prediccion", response.get("demanda_proyectada_horas"));
        model.addAttribute("mensaje", response.get("mensaje"));
        model.addAttribute("grafico", response.get("grafico_base64"));
        model.addAttribute("cantidadReservas", reservas.size()); // 👈 nueva línea

        return "prediccion";
    }
}
