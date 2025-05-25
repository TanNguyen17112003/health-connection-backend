package com.example.health_connection.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.health_connection.exceptions.InvalidTokenException;
import com.example.health_connection.models.User;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String JWT_SECRET;

    @Value("${security.jwt.token.expire-length}")
    private long JWT_EXPIRE_LENGTH;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create()
                    .withClaim("userId", user.getUser_id())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRE_LENGTH))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating token", exception);
        }
    }

    public Long validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getClaim("userId")
                    .asLong();
        } catch (AlgorithmMismatchException | InvalidClaimException exception) {
            throw new InvalidTokenException();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating token", exception);
        }
    }
}