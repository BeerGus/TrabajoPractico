package dev.danvega.h2demo.controller;

import dev.danvega.h2demo.model.Persona;
import dev.danvega.h2demo.repository.PersonaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/personas") // Ruta base: http://localhost:8080/api/personas
public class PersonaController {

    private final PersonaRepository repository;

    // Inyección de dependencias para usar el repositorio
    public PersonaController(PersonaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Persona> listarPersonas() {
        // Usa el método findAll() heredado para obtener todos los usuarios
        return repository.findAll();
    }
}