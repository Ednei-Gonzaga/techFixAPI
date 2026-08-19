package com.dev.ednei.techFixApi.infra.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.net.URI;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity handlerCreateTokenError(JWTCreationException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Internal Error Server");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity handlerVerificationToken(JWTVerificationException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Unauthorized");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(AccessForbiddenException.class)
    public ResponseEntity accessForbiddenHandler(AccessForbiddenException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setTitle("Access Forbidden");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity invalidParameterExceptionHandler(InvalidParameterException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Invalid Parameter");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public  ResponseEntity handlerEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Entity Not Found");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){
        var errors = ex.getFieldErrors().stream().map(ModelBeanValidationClass::new).toList();

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Um ou mais campos estão invalidos");
        problemDetail.setTitle("Invalid Parameter");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(ConflictDataException.class)
    public ResponseEntity handlerConflictDataException(ConflictDataException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Conflict Data");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity handlerUnprocessableEntityException(UnprocessableEntityException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
        problemDetail.setTitle("Unprocessable Entity");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(problemDetail);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handlerBadCredentialsException(BadCredentialsException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário inexistente ou senha inválida");
        problemDetail.setTitle("Bad Credentials");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity handlerInternalAuthenticationServiceException(InternalAuthenticationServiceException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário inexistente ou senha inválida");
        problemDetail.setTitle("Bad Credentials");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request){

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, " O corpo da requisição está inválido ou malformado. Verifique o formato dos dados enviados");
        problemDetail.setTitle("Bad Request");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(FirstAccessException.class)
    public ResponseEntity handlerFirstAccessException(FirstAccessException ex){
        var firstAccessDTO = new FirstAccessResponseDTO(ex.getMessage(), ex.getToken());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(firstAccessDTO);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity handlerDisabledException(DisabledException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Sua conta foi desativada. Entre em contato com o gerente do sistema");
        problemDetail.setTitle("Disabled Account");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request){
        var message = "O valor informado '"+ ex.getValue() +"' para o parâmetro '" + ex.getName() + "' é inválido";
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
        problemDetail.setTitle("Invalid Parameter Type");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity handlerNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Não foi encontrado o endpoint " + request.getRequestURI() + ". Verifique se a URL está correta");
        problemDetail.setTitle("Endpoint Not Found");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
}
