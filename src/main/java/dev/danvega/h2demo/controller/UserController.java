package dev.danvega.h2demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/api/me")
    public Map<String, Object> getAuthenticatedUser(Authentication auth) {
        if (auth == null) {
            return Map.of("authenticated", false);
        }

        // Extraemos email (username) y rol
        String email = auth.getName();
        String role = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        return Map.of(
                "authenticated", true,
                "email", email,
                "role", role.replace("ROLE_", "")
        );
    }
}
