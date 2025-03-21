package com.miproyecto.gamestack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/registro","/css/**","/js/**","/images/**","/bootstrap-icons/**","/verificar-cuenta","/h2-console/**").permitAll() // Permitir acceso a home y recursos públicos
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Restringir acceso según roles
                        .requestMatchers("/moderador/**").hasRole("MODERATOR")
                        .requestMatchers("/usuario/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                        .anyRequest().authenticated() // Todo lo demás requiere autenticación
                );
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)); // Permitir acceso a la consola de H2
        http.csrf(AbstractHttpConfigurer::disable); // Deshabilitar CSRF
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
