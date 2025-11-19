/*package dev.danvega.h2demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Usar @Controller para devolver nombres de plantillas (vistas)
public class ViewController {

    @GetMapping("/")
    public String showDashboard() {
        // Devuelve 'index', que Spring busca como 'index.html' en /templates/
        return "index";
    }
}

 */

package dev.danvega.h2demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    /**
     * Redirige la raíz "/" a la página de login clara (/login) para evitar bucles.
     * El contenido se sirve desde static/login.html.
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    /**
     * Sirve la página de login desde /login.html (ubicada en /static).
     * No hace falta template engine; es un forward a recurso estático.
     */
    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }
    // NUEVO: muestra la página de registro
    @GetMapping("/register")
    public String register() {
        // Va a buscar templates/register.html
        return "register";
    }

}
