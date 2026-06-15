package com.dev.ednei.techFixApi.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v2/auth/login").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v2/employees/me").hasAnyRole("MANAGER", "TECHNICAL", "ATTENDANT")
                        .requestMatchers(HttpMethod.GET, "/api/v2/employees/me").hasAnyRole("MANAGER", "TECHNICAL", "ATTENDANT")
                        .requestMatchers(HttpMethod.POST, "/api/v2/employees/search/cpf").hasAnyRole("MANAGER", "ATTENDANT")
                        .requestMatchers(HttpMethod.GET, "/api/v2/employees/*").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/v2/employees/*").hasRole("MANAGER")
                        .requestMatchers( "/api/v2/employees").hasRole("MANAGER")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
