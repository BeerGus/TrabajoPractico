package dev.danvega.h2demo.controller;

import dev.danvega.h2demo.model.Sala;
import dev.danvega.h2demo.repository.SalaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RestController // Indica que los métodos devuelven datos (como JSON), no vistas HTML
@RequestMapping("/api/salas") // Mapea todas las peticiones a la ruta base /api/salas
public class SalaController {

    private final SalaRepository repository;

    // Inyección de dependencias (Constructor Injection):
    // Spring crea e inyecta la instancia de SalaRepository automáticamente.
    public SalaController(SalaRepository repository) {
        this.repository = repository;
    }

    @GetMapping // Mapea GET a /api/salas (ruta final)
    public List<Sala> listarSalas() {
        // La llamada a findAll() la proporciona Spring Data JPA
        return repository.findAll();
    }

    // El nuevo método que te da el error
    @PostMapping
    public Sala crearSala(@RequestBody Sala sala) {
        return repository.save(sala);
    }

}
