package com.dev.ednei.techFixApi.infra.security;

import com.dev.ednei.techFixApi.infra.exceptions.errors.CustomAuthenticationEntryPoint;
import com.dev.ednei.techFixApi.infra.exceptions.errors.CustumAccessDeniedHandler;
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

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustumAccessDeniedHandler custumAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v2/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v2/employees/search/cpf", "/api/v2/clients", "/api/v2/clients/cpf/search", "/api/v2/service-requests").hasAnyRole("MANAGER", "ATTENDANT")
                        .requestMatchers(HttpMethod.GET, "/api/v2/clients/*", "/api/v2/clients", "/api/v2/service-requests", "/api/v2/service-requests/clients/*","/api/v2/service-orders/*/payments").hasAnyRole("MANAGER", "ATTENDANT")
                        .requestMatchers(HttpMethod.PUT, "/api/v2/clients/*", "/api/v2/service-requests/*", "/api/v2/service-orders/*/payments").hasAnyRole("MANAGER", "ATTENDANT")
                        .requestMatchers(HttpMethod.GET, "/api/v2/employees/*", "/api/v2/employees", "/api/v2/admin/service-catalogs").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/v2/employees/*", "/api/v2/parts/*", "/api/v2/service-catalogs/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v2/parts/*/enable", "/api/v2/service-catalogs/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v2/service-orders/*/payments").hasAnyRole("MANAGER", "ATTENDANT")
                        .requestMatchers(HttpMethod.DELETE, "/api/v2/parts/*", "/api/v2/users/*", "/api/v2/service-catalogs/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/v2/service-orders/my-tasks").hasRole("TECHNICAL")
                        .requestMatchers(HttpMethod.POST, "/api/v2/parts", "/api/v2/users", "/api/v2/service-catalogs", "/api/v2/service-catalogs/**").hasRole("MANAGER")
                        .requestMatchers("/api/v2/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/v2/service-order-tasks", "/api/v2/service-order-tasks").hasAnyRole("MANAGER", "TECHNICAL")
                        .requestMatchers(HttpMethod.PUT, "/api/v2/employees/me", "/api/v2/users/me/password", "/api/v2/service-orders/*").hasAnyRole("MANAGER", "TECHNICAL", "ATTENDANT")
                        .requestMatchers(HttpMethod.GET, "/api/v2/service-order-items/service-order/*","/api/v2/employees/me", "/api/v2/parts/**", "/api/v2/parts", "/api/v2/service-catalogs", "/api/v2/service-requests/*", "/api/v2/service-orders", "/api/v2/service-orders/*").hasAnyRole("MANAGER", "TECHNICAL", "ATTENDANT")
                        .requestMatchers(HttpMethod.PATCH, "/api/v2/parts/*/quantity").hasAnyRole("MANAGER", "TECHNICAL", "ATTENDANT")
                        .requestMatchers("/api/v2/service-order-items/**", "/api/v2/service-order-items", "/api/v2/service-order-tasks/**", "/api/v2/service-order-tasks", "/api/v2/service-order/*/task").hasAnyRole("MANAGER", "TECHNICAL")
                        .anyRequest().permitAll()
                )
                .exceptionHandling(ex ->
                        ex.accessDeniedHandler(custumAccessDeniedHandler)
                                .authenticationEntryPoint(customAuthenticationEntryPoint))
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
