package dev.danvega.h2demo.controller;

import dev.danvega.h2demo.model.Persona;
import dev.danvega.h2demo.model.WhoAmIDTO;
import dev.danvega.h2demo.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;      // ✅ IMPORT CORRECTA
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping("/whoami")
    public ResponseEntity<?> whoAmI(Authentication authentication) {

        // 1) Si no hay usuario autenticado
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("No hay usuario autenticado.");
        }

        // 2) El username/email viene del Authentication
        String email = authentication.getName();

        // 3) PersonaRepository devuelve Optional<Persona>
        Optional<Persona> optionalPersona = personaRepository.findByEmail(email);

        if (optionalPersona.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la persona asociada al usuario.");
        }

        Persona persona = optionalPersona.get();

        // 4) Construimos el DTO con los datos que necesita el front
        WhoAmIDTO dto = new WhoAmIDTO(
                persona.getId(),         // idPersona
                persona.getNombre(),
                persona.getEmail(),
                persona.getRol()         // 👈 rol ya es String, sin .name()
        );

        return ResponseEntity.ok(dto);
    }
}
