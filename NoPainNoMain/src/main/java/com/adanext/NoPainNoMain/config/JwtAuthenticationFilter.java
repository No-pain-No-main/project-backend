package com.adanext.NoPainNoMain.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.adanext.NoPainNoMain.service.auth.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro que intercepta cada petición HTTP para validar el token JWT
 * y establecer la autenticación en el contexto de Spring Security.
 * 
 * Se ejecuta antes de que la petición llegue al controlador.
 * Si el token es válido, extrae el número de documento y el rol del usuario
 * y los registra en SecurityContextHolder para que los endpoints protegidos
 * puedan identificar quién hace la petición.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter() {
        this.jwtUtil = new JwtUtil();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Ignorar completamente las peticiones OPTIONS (preflight CORS)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        /*
         * El token JWT debe venir en el header:
         *   Authorization: Bearer <token>
         * 
         * El constructor UsernamePasswordAuthenticationToken recibe:
         *   - principal   → documentNumber del usuario autenticado
         *   - credentials → null (no se necesita la contraseña porque
         *                    el token JWT ya fue validado con su firma)
         *   - authorities → lista con el rol (ej: ROLE_STUDENT)
         * 
         * credentials se pasa como null porque no queremos almacenar
         * contraseñas en la sesión de seguridad — el JWT ya garantiza
         * que el usuario está autenticado y su rol es válido.
         */

        String header = request.getHeader(JwtParameters.AUTH_HEADER);

        if (header != null && header.startsWith(JwtParameters.BEARER_PREFIX)) {
            String token = header.substring(JwtParameters.BEARER_PREFIX_LENGTH);

            if (jwtUtil.validateToken(token)) {
                String documentNumber = jwtUtil.getDocumentNumber(token);
                String role = jwtUtil.getRole(token);

                var authorities = Collections.singletonList(
                        new SimpleGrantedAuthority(JwtParameters.ROLE_PREFIX + role)
                );

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(documentNumber, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}