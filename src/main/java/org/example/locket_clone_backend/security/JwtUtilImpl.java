package org.example.locket_clone_backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

// @Component
public class JwtUtilImpl implements JwtUtil {
    private final String secret = "hayashing_only_simp_kiana";

    @Override
    public String generateToken(Long id) {
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public Long verifyToken(String token) throws JWTVerificationException {
        System.out.println("Before verify token: " + token);
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
        Long id = Long.valueOf(decodedJWT.getSubject());
        System.out.println("After verify token: " + token);
        return id;
    }


}
