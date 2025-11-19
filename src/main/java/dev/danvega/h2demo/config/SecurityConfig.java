package dev.danvega.h2demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // habilita @PreAuthorize si luego querés usarlo en controladores
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Solo para DEV (H2 + formulario simple)
                .headers(h -> h.frameOptions(f -> f.sameOrigin()))
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Público
                        .requestMatchers("/", "/login", "/login.html", "/index.html",
                                "/register", "/register.html",
                                "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        // ===== POLÍTICAS POR ROL =====
                        // Solo ADMIN puede ver/gestionar personas
                        .requestMatchers("/api/personas", "/api/personas/**").hasRole("ADMIN")

                        // Salas y Reservas: ADMIN o USER (ambos roles)
                        .requestMatchers("/api/salas", "/api/salas/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/api/reservas", "/api/reservas/**").hasAnyRole("ADMIN","USER")

                        // Cualquier otra API requiere login (pero no un rol específico)
                        .requestMatchers("/api/**").authenticated()

                        // Lo demás, autenticado
                        .anyRequest().authenticated()
                )

                // Login  página /login (servimos login.html)
               /*
                .formLogin(form -> form
                        .loginPage("/login")              // GET
                        .loginProcessingUrl("/login")     // POST del form
                        .defaultSuccessUrl("/index.html", true)
                        .permitAll()
                )*/
                .formLogin(form -> form
                        .loginPage("/login")              // GET: muestra login.html
                        .loginProcessingUrl("/login")     // POST: procesa el form
                        .defaultSuccessUrl("/index.html", true) // destino al loguearse
                        .failureUrl("/login?error=true")  // muestra el mensaje de error
                        .permitAll()
                )

                // Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )

                // 403 si el rol no alcanza
                .exceptionHandling(e -> e.accessDeniedPage("/login?denied"));
        return http.build();
    }

    // SOLO DEV: texto plano
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
