package dev.danvega.h2demo.repository;

import dev.danvega.h2demo.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Optional<Persona> findByEmail(String email);
}
