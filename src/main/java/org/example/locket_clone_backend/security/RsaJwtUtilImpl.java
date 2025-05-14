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
import lombok.extern.java.Log;

@Component
@Log
public class RsaJwtUtilImpl implements JwtUtil {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    private static RSAPublicKey readX509PublicKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.decodeBase64(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private RSAPrivateKey readPKCS8PrivateKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.decodeBase64(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    @PostConstruct
    public void init() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL privateRes = classLoader.getResource("keys/private_key.pem");
            URL publicRes = classLoader.getResource("keys/public_key.pem");
            privateKey = readPKCS8PrivateKey(new File(privateRes.getFile()));
            publicKey = readX509PublicKey(new File(publicRes.getFile()));
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
