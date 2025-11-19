/*package dev.danvega.h2demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas") // Mapea a la tabla 'personas'
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Corresponde a id INT PRIMARY KEY AUTO_INCREMENT

    private String nombre; // Corresponde a nombre VARCHAR(255) NOT NULL

    private String email; // Corresponde a email VARCHAR(255) UNIQUE NOT NULL

    // Constructor vacío (necesario para JPA)
    public Persona() {
    }

    // Constructor sin ID
    public Persona(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // --- Getters y Setters (Necesarios) ---
    // (Puedes generarlos en IntelliJ con Alt+Insert o Cmd+N)

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

package dev.danvega.h2demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas") // Mapea a la tabla 'personas'
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String email;

    // 🔒 CAMPOS NUEVOS PARA SPRING SECURITY 🔒
    private String password; // Contraseña hasheada (BCrypt)
    private String rol;      // Rol del usuario: "ADMIN" o "USER"

    // Constructor vacío (necesario para JPA)
    public Persona() {
    }

    // Constructor completo (sin ID, útil para crear nuevas personas)
    public Persona(String nombre, String email, String password, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Getters y Setters para Seguridad
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

 */
package dev.danvega.h2demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "personas", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // TEXTO PLANO (solo DEV)

    // Valores esperados en BD: "ADMIN" o "USER"
    @Column(nullable = false)
    private String rol;

    public Persona() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
