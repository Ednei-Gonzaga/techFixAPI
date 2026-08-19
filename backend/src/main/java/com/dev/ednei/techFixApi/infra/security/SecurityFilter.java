package com.dev.ednei.techFixApi.infra.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.AccessForbiddenException;
import com.dev.ednei.techFixApi.repository.UserRepository;
import com.dev.ednei.techFixApi.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = retornTokenWithoutBearer(request);

        try {
            if (token != null) {
                var subject = tokenService.verifierTokenJwt(token);

                checkTokenIsUpdate(token, request);

                var user = userRepository.findByIdForAuthentication(Long.parseLong(subject));

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }catch (AccessForbiddenException | JWTVerificationException e){
            exceptionResolver.resolveException(request, response, null, e);
        }

    }

    private String retornTokenWithoutBearer(HttpServletRequest request) {
        var token = request.getHeader("Authorization");

        if (!StringUtils.hasText(token)) {
            return null;
        }

        return token.replace("Bearer ", "");
    }

    private void checkTokenIsUpdate(String token, HttpServletRequest request) {
        var objectToken = tokenService.decodeTokenJwt(token);
        var tokenIsUpdate = objectToken.getClaim("scope").asString().equals("force_update");
        String pathRequest = request.getRequestURI();

        if (Integer.parseInt(objectToken.getSubject()) != 1 && tokenIsUpdate && (!pathRequest.equals("/api/v2/users/me/password"))) {
            throw new AccessForbiddenException("O token e exclusivo para rotas de atualização de senha");
        }

        if (Integer.parseInt(objectToken.getSubject()) == 1 && tokenIsUpdate && (!pathRequest.equals("/api/v2/users/me/password") && !pathRequest.equals("/api/v2/employees/1") && !pathRequest.equals("/employees/me"))) {
            throw new AccessForbiddenException("O token e exclusivo para rotas de atualização de senha e de dados do usuario/funcionario.");
        }

    }
}
