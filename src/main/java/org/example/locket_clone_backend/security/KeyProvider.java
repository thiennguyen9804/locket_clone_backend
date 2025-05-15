package org.example.locket_clone_backend.security;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class KeyProvider {
    private RSAPublicKey readX509PublicKey(File file) throws Exception {
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

    public RSAPrivateKey getPrivateKey() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL privateRes = classLoader.getResource("keys/private_key.pem");
            return readPKCS8PrivateKey(new File(privateRes.getFile()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public RSAPublicKey getPublicKey() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL publicRes = classLoader.getResource("keys/public_key.pem");
            return readX509PublicKey(new File(publicRes.getFile()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private String sha1Base64Url(PublicKey publicKey) throws Exception {
        byte[] encoded = publicKey.getEncoded(); // DER-encoded
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] digest = sha1.digest(encoded);
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    public String hashKey(PublicKey publicKey) {
        try {
            return sha1Base64Url(publicKey);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
