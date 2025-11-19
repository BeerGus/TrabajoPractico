package dev.danvega.h2demo.controller;

import dev.danvega.h2demo.model.Persona;
import dev.danvega.h2demo.repository.PersonaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final PersonaRepository personaRepository;

    public RegistrationController(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {

        // 1) Validar que no exista el email
        if (personaRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Ya existe un usuario registrado con ese email.");
            model.addAttribute("nombre", nombre);
            model.addAttribute("email", email);
            // Volvemos a la vista de registro con el mensaje de error
            return "register";
        }

        // 2) Crear nueva Persona con rol USER
        Persona p = new Persona();
        p.setNombre(nombre);
        p.setEmail(email);
        p.setPassword(password); // TEXTO PLANO (NoOpPasswordEncoder, solo para DEV)
        p.setRol("USER");

        personaRepository.save(p);

        // 3) Redirigir a login con un indicador de registro exitoso
        return "redirect:/login?registered=true";
    }
}
