package com.miproyecto.gamestack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/registro","/reenviar-codigo","/css/**","/js/**","/images/**","/bootstrap-icons/**","/verificar-cuenta").permitAll() // Permitir acceso a home y recursos públicos
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Restringir acceso según roles
                        .requestMatchers("/moderador/**").hasRole("MODERATOR")
                        .requestMatchers("/h2-console/**").hasRole("USER")
                        .anyRequest().authenticated() // Todo lo demás requiere autenticación
                )
                .requiresChannel(channel -> channel.anyRequest().requiresSecure())
                //https
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll) // Activa la página de login predeterminada
                .logout(LogoutConfigurer::permitAll); // Habilita el logout
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)); // Permitir acceso a la consola de H2
        http.csrf(AbstractHttpConfigurer::disable); // Deshabilitar CSRF
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
