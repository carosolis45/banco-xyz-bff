package com.bancoxyz.banco_xyz_bff.config;

import com.bancoxyz.banco_xyz_bff.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * Configuración del AuthenticationManager y PasswordEncoder.
 * Usa DelegatingPasswordEncoder para soportar {noop}, {bcrypt}, etc.
 */
@Configuration
public class SecurityBeansConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityBeansConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * DelegatingPasswordEncoder: soporta {noop}, {bcrypt}, {pbkdf2}, etc.
     * Con este encoder, {noop}web123 se interpreta correctamente.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(daoAuthenticationProvider()));
    }
}