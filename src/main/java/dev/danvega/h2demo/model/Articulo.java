package dev.danvega.h2demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "articulos")
public class Articulo {

    @Id
    private Integer id;   // en schema.sql está como PRIMARY KEY AUTO_INCREMENT

    private String nombre;
    private Boolean disponible;

    public Articulo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}

