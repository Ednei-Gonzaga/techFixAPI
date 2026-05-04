package com.dev.ednei.techFixApi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dev.ednei.techFixApi.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Value("${PASSWORD_TOKEN}")
    private String password;

    public String authenticationToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(password);
        return JWT.create()
                .withIssuer("tech-api")
                .withSubject(user.getLogin())
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole().name())
                .sign(algorithm);
    }

    public String authorizationToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            return JWT.require(algorithm)
                    .withIssuer("tech-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException ex) {
            return ex.getMessage();
        }

    }
}
