package dev.danvega.h2demo.service;

import dev.danvega.h2demo.model.Persona;
import dev.danvega.h2demo.repository.PersonaRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonaRepository repo;

    public CustomUserDetailsService(PersonaRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Persona p = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario: " + email));

        // Mapeamos "ADMIN" -> "ROLE_ADMIN" y "USER" -> "ROLE_USER"
        String role = p.getRol();
        String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;

        return User.withUsername(p.getEmail())
                .password(p.getPassword()) // TEXTO PLANO (NoOpPasswordEncoder)
                .authorities(List.of(new SimpleGrantedAuthority(authority)))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
