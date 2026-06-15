package com.dev.ednei.techFixApi.infra.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(AccessForbiddenException.class)
    public ResponseEntity accessForbiddenHandler(AccessForbiddenException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setTitle("Access Forbidden");
        problemDetail.setInstance(URI.create("/src/techFix-api"));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity invalidParameterExceptionHandler(InvalidParameterException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Invalid Parameter");
        problemDetail.setInstance(URI.create("/src/techFix-api"));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public  ResponseEntity handlerEntityNotFoundException(EntityNotFoundException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Entity Not Found");
        problemDetail.setInstance(URI.create("/src/techFix-api"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        var errors = ex.getFieldErrors().stream().map(ModelBeanValidationClass::new).toList();

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Um ou mais campos estão invalidos");
        problemDetail.setTitle("Invalid Parameter");
        problemDetail.setInstance(URI.create("/src/techFix-api"));
        problemDetail.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(ConflictDataException.class)
    public ResponseEntity handlerConflictDataException(ConflictDataException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Conflict Data");
        problemDetail.setInstance(URI.create("/src/techFix-api"));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

}
