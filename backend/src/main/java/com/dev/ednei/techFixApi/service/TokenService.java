package com.dev.ednei.techFixApi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Value("${PASSWORD_TOKEN}")
    private String passwordTokenAlgorithm;

    public String createTokenJwt( ){
        try {
            Algorithm algorithm = Algorithm.HMAC256(passwordTokenAlgorithm);
            return JWT.create()
                    .withIssuer("techFix-api")
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Houve um erro ao gerar token jwt", exception);
        }
    }

    public String verifierTokenJwt(String tokenJwt){
        try {
            Algorithm algorithm = Algorithm.HMAC256(passwordTokenAlgorithm);
            return JWT.require(algorithm)
                    .withIssuer("techFix-api")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token invalido ou expirado");
        }
    }


}
