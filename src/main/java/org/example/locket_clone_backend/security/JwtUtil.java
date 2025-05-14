package org.example.locket_clone_backend.security;

import com.auth0.jwt.exceptions.JWTVerificationException;

public interface JwtUtil {
    public abstract String generateToken(Long id);
    public abstract Long verifyToken(String token) throws JWTVerificationException;
}
