package dev.danvega.h2demo.controller;

import dev.danvega.h2demo.model.Articulo;
import dev.danvega.h2demo.repository.ArticuloRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {

    private final ArticuloRepository articuloRepository;

    public ArticuloController(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    @GetMapping
    public List<Articulo> listarArticulos() {
        return articuloRepository.findAll();
    }
}
