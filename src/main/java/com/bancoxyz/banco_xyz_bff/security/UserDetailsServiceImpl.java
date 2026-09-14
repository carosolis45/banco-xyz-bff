package com.bancoxyz.banco_xyz_bff.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio que carga usuarios y sus roles.
 * 
 * NOTA: Para desarrollo se usa {noop} (contraseña sin encriptar).
 * En producción debe usarse BCrypt.
 * 
 * Usuarios disponibles:
 *   - webuser   / web123    → Rol: WEB_USER
 *   - moviluser / movil123  → Rol: MOVIL_USER
 *   - cajerouser / cajero123 → Roles: CAJERO_CONSULTA, CAJERO_RETIRO
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // ==========================================
        // USUARIO WEB
        // ==========================================
        if ("webuser".equals(username)) {
            return User.builder()
                    .username("webuser")
                    .password("{noop}web123")
                    .roles("WEB_USER")
                    .build();
        }

        // ==========================================
        // USUARIO MÓVIL
        // ==========================================
        if ("moviluser".equals(username)) {
            return User.builder()
                    .username("moviluser")
                    .password("{noop}movil123")
                    .roles("MOVIL_USER")
                    .build();
        }

        // ==========================================
        // USUARIO CAJERO
        // ==========================================
        if ("cajerouser".equals(username)) {
            return User.builder()
                    .username("cajerouser")
                    .password("{noop}cajero123")
                    .roles("CAJERO_CONSULTA", "CAJERO_RETIRO")
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}