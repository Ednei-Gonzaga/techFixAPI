package com.dev.ednei.techFixApi.infra.exceptions.errors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;

@Component
public class CustumAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(403);
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);


        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar este recurso");
        problemDetail.setTitle("Access Denied");
        problemDetail.setInstance(URI.create("/api/v1/auth/login"));

        ObjectMapper  mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(problemDetail));
    }
}
