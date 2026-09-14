package com.bancoxyz.banco_xyz_bff.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro que intercepta cada petición y valida el token JWT.
 * Si el token es válido, autentica al usuario en el contexto de Spring Security.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtener el header Authorization
        final String authHeader = request.getHeader("Authorization");

        // 2. Si no hay token, continuar sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token (quitar "Bearer ")
        final String token = authHeader.substring(7);
        String username = null;
        String rol = null;

        try {
            username = jwtUtil.extraerUsername(token);
            rol = jwtUtil.extraerRol(token);
        } catch (Exception e) {
            // Token inválido o expirado, continuar sin autenticar
            filterChain.doFilter(request, response);
            return;
        }

        // 4. Si hay username y no hay autenticación previa, autenticar
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (jwtUtil.validarToken(token, username)) {
                // Crear la autenticación con el rol del token
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority(rol))
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer la autenticación en el contexto
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 5. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}