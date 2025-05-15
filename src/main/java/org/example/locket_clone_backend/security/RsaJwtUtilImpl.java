package org.example.locket_clone_backend.security;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Component
@Log
@RequiredArgsConstructor
public class RsaJwtUtilImpl implements JwtUtil {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;
    private final KeyProvider keyProvider;
    private String[] aud = new String[] {"powersync-dev", "powersync"};

    

    @PostConstruct
    public void init() {
        try {
            privateKey = keyProvider.getPrivateKey();
            publicKey = keyProvider.getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Error reading key");
        }
    }

    @Override
    public String generateToken(Long id) {
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withAudience(aud)
                .sign(Algorithm.RSA256(privateKey));
    }

    @Override
    public Long verifyToken(String token) throws JWTVerificationException {
        DecodedJWT jwt = JWT.require(Algorithm.RSA256(publicKey, null)) // public để verify
                .build()
                .verify(token);
        return Long.parseLong(jwt.getSubject());
    }

}
