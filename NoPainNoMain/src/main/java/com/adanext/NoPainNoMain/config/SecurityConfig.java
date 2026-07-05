package com.adanext.NoPainNoMain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de Spring Security.
 * 
 * Define qué rutas son públicas (login) y cuáles requieren autenticación
 * según el rol del usuario (STUDENT, ADMIN, VALIDATOR).
 * 
 * Deshabilita CSRF porque es una API REST sin estado (stateless).
 * Las sesiones se manejan mediante tokens JWT, no cookies de sesión.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                /*
                 * Rutas públicas:
                 *   - /api/auth/**  → login (student, admin, validator)
                 *   - /api/test/**  → respaldo (TestJsonController, sin token)
                 */
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()

                /*
                 * Rutas protegidas por rol específico:
                 *   - /api/administrators/** → solo ADMIN
                 *   - /api/machines/**       → solo ADMIN
                 *   - /api/validator/**     → solo VALIDATOR
                 */
                .requestMatchers("/api/administrators/**").hasRole(JwtParameters.ROLE_ADMIN)
                .requestMatchers("/api/machines/**").hasRole(JwtParameters.ROLE_ADMIN)
                .requestMatchers("/api/validator/**").hasRole(JwtParameters.ROLE_VALIDATOR)

                /*
                 * Rutas accesibles con cualquier autenticación (sin restricción de rol):
                 *   - /api/students/**     → consultar y registrar estudiantes
                 *   - /api/bookings/**     → crear, cancelar y consultar reservas
                 *   - /api/availability/** → consultar disponibilidad
                 */
                .requestMatchers("/api/students/**").authenticated()
                .requestMatchers("/api/bookings/**").authenticated()
                .requestMatchers("/api/availability/**").authenticated()

                /*
                 * Cualquier otra ruta requiere autenticación.
                 */
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}