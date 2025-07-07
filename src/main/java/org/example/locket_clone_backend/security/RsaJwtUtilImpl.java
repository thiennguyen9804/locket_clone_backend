package org.example.locket_clone_backend.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class RsaJwtUtilImpl implements JwtUtil {

  private RSAPrivateKey privateKey;
  private RSAPublicKey publicKey;
  private final KeyProvider keyProvider;
  private String[] aud = new String[] { "powersync-dev", "powersync" };

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
