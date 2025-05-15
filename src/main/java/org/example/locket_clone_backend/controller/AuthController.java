package org.example.locket_clone_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.example.locket_clone_backend.domain.dto.JwksDto;
import org.example.locket_clone_backend.domain.dto.SignInReq;
import org.example.locket_clone_backend.domain.dto.SignInRes;
import org.example.locket_clone_backend.domain.dto.SignUpReq;
import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.example.locket_clone_backend.security.KeyProvider;
import org.example.locket_clone_backend.security.RsaJwtUtilImpl;
import org.example.locket_clone_backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final KeyProvider keyProvider;

    @PostMapping("/sign-in")
    @Operation(summary = "User sign in")
    public ResponseEntity<Object> signIn(@RequestBody SignInReq signIn) {
        System.out.println(signIn.toString());
        try {
            SignInRes res = authService.signIn(signIn);
            return ResponseEntity.ok(res);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Email or Password is wrong", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sign-up")
    @Operation(summary = "User sign up")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpReq signUp) {
        System.out.println(signUp.email);
        try {
            UserDto user = authService.signUp(signUp);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            if (Objects.equals(e.getMessage(), "User already exists"))
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    @Operation(summary = "Get current user")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDto res = authService.getCurrentUser();
        return ResponseEntity.ok(res);
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/keys")
    @Operation(summary = "Provide keys for PowerSync")
    public JwksDto getKeys(@RequestParam String param) {
        RSAPublicKey publicKey = keyProvider.getPublicKey();
        String hash = keyProvider.hashKey(publicKey);
        String n = Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getModulus().toByteArray());
        String e = Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getPublicExponent().toByteArray());

        final JwksDto key = JwksDto.builder()
            .kid(hash)
            .kty("RSA")
            .alg("RS256")
            .use("sig")
            .x5c(null)
            .n(n)
            .e(e)
            .x5t(hash)
            .build();
        return key;
    }

}
