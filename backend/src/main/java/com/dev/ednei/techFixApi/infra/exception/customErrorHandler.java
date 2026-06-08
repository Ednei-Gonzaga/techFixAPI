package com.dev.ednei.techFixApi.infra.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dev.ednei.techFixApi.infra.exception.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exception.errors.ForbiddenOperationException;
import com.dev.ednei.techFixApi.infra.exception.errors.InvalidParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Map;

@RestControllerAdvice
public class customErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerBadRequest(MethodArgumentNotValidException ex){
        var listErrors = ex.getFieldErrors();
        var fields = listErrors.stream().map(DataError400::new).toList();

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Um ou mais campos incorretos");
        problemDetail.setTitle("Invalid Field");
        problemDetail.setInstance(URI.create("/techfix-api/bad-request"));
        problemDetail.setProperties(Map.of("errors", fields));

       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handlerEntityNotFound(EntityNotFoundException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Entity Not Found");
        problemDetail.setInstance(URI.create("/techfix-api/entity-not-found"));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity handlerInvalidParam(InvalidParameterException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Invalid Parameter");
        problemDetail.setInstance(URI.create("/techfix-api/invalid-parameter"));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity handlerForbiddenOperation(ForbiddenOperationException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
        problemDetail.setTitle("action not permitted by business rule");
        problemDetail.setInstance(URI.create("/techfix-api/action-not-permitted"));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(problemDetail);
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ProblemDetail> handleErrorJWTCreation(JWTCreationException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Token JWT Creation Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity handlerJWTVerificationException(JWTVerificationException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("invalid token");
        problemDetail.setInstance(URI.create("/api/token-invalid"));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ProblemDetail>  userNameException(AuthenticationException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Login ou senha incorretos");
        problemDetail.setTitle("User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }


    private record  DataError400(String field, String message){
        public DataError400(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
