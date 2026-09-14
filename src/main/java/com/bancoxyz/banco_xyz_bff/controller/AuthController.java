package com.bancoxyz.bff.controller;

import com.bancoxyz.banco_xyz_bff.security.JwtUtil;
import com.bancoxyz.bff.dto.LoginRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de autenticación con validaciones.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login/web")
    public ResponseEntity<?> loginWeb(@Valid @RequestBody LoginRequestDTO request) {
        return autenticar(request, "WEB_USER");
    }

    @PostMapping("/login/movil")
    public ResponseEntity<?> loginMovil(@Valid @RequestBody LoginRequestDTO request) {
        return autenticar(request, "MOVIL_USER");
    }

    @PostMapping("/login/cajero")
    public ResponseEntity<?> loginCajero(@Valid @RequestBody LoginRequestDTO request) {
        return autenticar(request, "CAJERO_CONSULTA");
    }

    private ResponseEntity<?> autenticar(LoginRequestDTO request, String rolRequerido) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            boolean tieneRol = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(rol -> rol.equals("ROLE_" + rolRequerido));

            if (!tieneRol) {
                return ResponseEntity.status(403).body(
                        Map.of("error", "El usuario no tiene permisos para este canal")
                );
            }

            String token = jwtUtil.generarToken(
                    auth.getName(),
                    auth.getAuthorities().iterator().next().getAuthority()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", auth.getName());
            response.put("rol", auth.getAuthorities().iterator().next().getAuthority());
            response.put("mensaje", "Autenticación exitosa");

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(
                    Map.of("error", "Usuario o contraseña incorrectos")
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("error", "Error interno: " + e.getMessage())
            );
        }
    }
}