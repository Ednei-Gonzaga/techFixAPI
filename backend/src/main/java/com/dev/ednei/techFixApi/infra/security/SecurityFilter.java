package com.dev.ednei.techFixApi.infra.security;

import com.dev.ednei.techFixApi.repository.UserRepository;
import com.dev.ednei.techFixApi.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     var token = retornTokenWithoutBearer(request);

     if(token != null){
         var subject = tokenService.verifierTokenJwt(token);

         var user = userRepository.findByIdForAuthentication(Long.parseLong(subject));

         var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
         SecurityContextHolder.getContext().setAuthentication(authentication);
     }
     filterChain.doFilter(request, response);
    }

    private String retornTokenWithoutBearer(HttpServletRequest request){
        var token = request.getHeader("Authorization");

        if(!StringUtils.hasText(token)){
            return null;
        }

        return token.replace("Bearer ", "");
    }
}
