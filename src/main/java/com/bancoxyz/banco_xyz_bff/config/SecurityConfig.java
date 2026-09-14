package com.bancoxyz.banco_xyz_bff.config;

import com.bancoxyz.banco_xyz_bff.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    // ============================================================
    // CADENA 1: BFF WEB
    // ============================================================
    @Bean
    @Order(1)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/web/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/web/").permitAll()
                .anyRequest().hasRole("WEB_USER")
            )
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(daoAuthenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // ============================================================
    // CADENA 2: BFF MÓVIL
    // ============================================================
    @Bean
    @Order(2)
    public SecurityFilterChain movilFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/movil/**")
            .authorizeHttpRequests(auth -> auth
                .anyRequest().hasRole("MOVIL_USER")
            )
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(daoAuthenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // ============================================================
    // CADENA 3: BFF CAJERO
    // ============================================================
    @Bean
    @Order(3)
    public SecurityFilterChain cajeroFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/cajero/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/cajero/saldo/**").hasRole("CAJERO_CONSULTA")
                .requestMatchers("/api/cajero/cuenta/**").hasRole("CAJERO_CONSULTA")
                .requestMatchers("/api/cajero/transacciones/**").hasRole("CAJERO_CONSULTA")
                .anyRequest().authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(daoAuthenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // ============================================================
    // CADENA 4: Autenticación (login) + H2 Console - Público
    // ============================================================
    @Bean
    @Order(4)
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/auth/**", "/h2-console/**")
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authenticationProvider(daoAuthenticationProvider);
        return http.build();
    }
}