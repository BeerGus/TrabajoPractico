package dev.danvega.h2demo.model;

public class WhoAmIDTO {
    public Integer idPersona;
    public String nombre;
    public String email;
    public String rol;

    public WhoAmIDTO(Integer idPersona, String nombre, String email, String rol) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }
}
