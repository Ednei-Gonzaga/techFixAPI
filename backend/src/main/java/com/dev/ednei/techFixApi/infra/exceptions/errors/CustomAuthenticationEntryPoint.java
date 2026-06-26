package com.dev.ednei.techFixApi.infra.exceptions.errors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

         var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Você precisa estar logado e enviar um token válido");
         problemDetail.setTitle("Access Unauthorized");
         problemDetail.setInstance(URI.create("/api/v1/auth/login"));

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));

    }
}
