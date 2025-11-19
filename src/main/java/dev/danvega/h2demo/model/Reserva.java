package dev.danvega.h2demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;// 👈 New en 2.1
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Los campos deben coincidir con tu schema.sql (JPA mapea camelCase a snake_case)
    private Integer idArticulo;
    private Integer idSala;
    private Integer idPersona;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    @Column(name = "estado_sala")   // 👈 FORZAMOS EL NOMBRE EXACTO DE LA COLUMNA
    private String estadoSala;
    public Reserva() {}

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdArticulo() { return idArticulo; }
    public void setIdArticulo(Integer idArticulo) { this.idArticulo = idArticulo; }

    public Integer getIdSala() { return idSala; }
    public void setIdSala(Integer idSala) { this.idSala = idSala; }

    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }

    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }

    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) { this.fechaHoraFin = fechaHoraFin; }

    public String getEstadoSala() {return estadoSala;}
    public void setEstadoSala(String estado) {this.estadoSala = estado;}

}