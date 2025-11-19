package dev.danvega.h2demo.repository;

import dev.danvega.h2demo.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {
}
