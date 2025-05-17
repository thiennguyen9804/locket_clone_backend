package org.example.locket_clone_backend.controller;

import java.util.List;

import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.service.AuthService;
import org.example.locket_clone_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/friends")
    @Operation(summary = "Get user's friend")
    public List<UserDto> getFriends() {
        UserDto userDto = authService.getCurrentUser();
        List<UserDto> res = userService.getFriends(userDto.id);
        return res;
    }

    @PostMapping("/friends/sent")
    @Operation(summary = "Get all user who I've made friend request")
    public List<UserDto> getSentRequest() {
        UserDto userDto = authService.getCurrentUser();
        List<UserDto> res = userService.getSentRequestFriends(userDto.id);
        return res;
    }
    @PostMapping("/friends/received")
    @Operation(summary = "Get all user who's made friend request to me")
    public List<UserDto> getReceivedRquest() {
        UserDto userDto = authService.getCurrentUser();
        List<UserDto> res = userService.getReceivedRequestFriends(userDto.id);
        return res;
    }
}
