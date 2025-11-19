package dev.danvega.h2demo.repository;

import dev.danvega.h2demo.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// La interfaz extiende JpaRepository, pasándole la Entidad (Sala) y el tipo de su ID (Integer)
@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer> {

    // Con esto, Spring te proporciona automáticamente todos los métodos CRUD (Crear, Leer, Actualizar, Eliminar).
}