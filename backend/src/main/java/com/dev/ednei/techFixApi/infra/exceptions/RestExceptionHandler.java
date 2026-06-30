package com.dev.ednei.techFixApi.infra.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity handlerUnprocessableEntityException(UnprocessableEntityException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
        problemDetail.setTitle("Unprocessable Entity");
        problemDetail.setInstance(URI.create("/src/techFix-api"));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(problemDetail);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handlerBadCredentialsException(BadCredentialsException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário inexistente ou senha inválida");
        problemDetail.setTitle("Bad Credentials");
        problemDetail.setInstance(URI.create("/api/v2/auth/login"));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex){

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, " O corpo da requisição está inválido ou malformado. Verifique o formato dos dados enviados");
        problemDetail.setTitle("Bad Request");
        problemDetail.setInstance(URI.create("/texhFix-apiRest"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(FirstAccessException.class)
    public ResponseEntity handlerFirstAccessException(FirstAccessException ex){
        var message = "É necessário atualizar a senha no primeiro acesso. Use o token enviado com validade de 10 minutos e acesse a rota '/api/v2/users/me/password' para atualizar.";
        var firstAccessDTO = new FirstAccessResponseDTO(message, ex.getToken());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(firstAccessDTO);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity handlerDisabledException(DisabledException ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Sua conta foi desativada. Entre em contato com o gerente do sistema");
        problemDetail.setTitle("Disabled Account");
        problemDetail.setInstance(URI.create("/api/v2/auth/login"));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }
}
