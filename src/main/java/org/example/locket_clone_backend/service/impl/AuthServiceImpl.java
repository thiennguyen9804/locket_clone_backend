package org.example.locket_clone_backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.example.locket_clone_backend.domain.dto.SignInReq;
import org.example.locket_clone_backend.domain.dto.SignInRes;
import org.example.locket_clone_backend.domain.dto.SignUpReq;
import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.example.locket_clone_backend.repository.UserRepository;
import org.example.locket_clone_backend.security.JwtUtil;
import org.example.locket_clone_backend.security.JwtUtilImpl;
import org.example.locket_clone_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@Service
@Log
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final Mapper<UserEntity, UserDto> userMapper;

    @Override
    public SignInRes signIn(SignInReq signInReq) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInReq.getLoginInfo(),
                        signInReq.getPassword()));
        var user = userRepository.findByEmail(signInReq.loginInfo).orElseThrow();
        UserDto userDto = userMapper.mapTo(user);

        String token = jwtUtil.generateToken(user.id);
        jwtUtil.verifyToken(token);
        return SignInRes.builder()
                .token(token)
                .user(userDto)
                .build();
    }

    @Override
    public UserDto signUp(SignUpReq signUpReq) throws RuntimeException {
        Optional<UserEntity> findUser = userRepository.findByEmail(signUpReq.email);
        if (findUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .name(signUpReq.name)
                .phoneNumber(signUpReq.phoneNumber)
                .email(signUpReq.email)
                .build();
        userEntity.password = passwordEncoder.encode(signUpReq.password);
        System.out.println(userEntity.email);
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.mapTo(savedUser);
    }

    @Override
    public UserDto getCurrentUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserEntity userEntity) {
            UserDto userDto = userMapper.mapTo(userEntity);
            System.out.println(userEntity);
            return userDto;
        }

        throw new RuntimeException("Cannot cast user in auth service impl getCurrentUser");

    }

}
