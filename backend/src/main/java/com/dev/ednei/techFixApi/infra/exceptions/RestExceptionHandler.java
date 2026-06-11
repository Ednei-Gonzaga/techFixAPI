package com.dev.ednei.techFixApi.infra.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity handlerCreateTokenError(JWTCreationException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Internal Error Server");
        problemDetail.setInstance(URI.create("/service/torkenService"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity handlerVerificationToken(JWTVerificationException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Unauthorized");
        problemDetail.setInstance(URI.create("/service/torkenService"));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }
}
