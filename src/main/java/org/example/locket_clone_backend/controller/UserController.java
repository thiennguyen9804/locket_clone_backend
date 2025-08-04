package org.example.locket_clone_backend.controller;

import java.util.List;

import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.service.AuthService;
import org.example.locket_clone_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserController {
  private final AuthService authService;
  private final UserService userService;

  @GetMapping("/friends")
  @Operation(summary = "Get user's friend", security = @SecurityRequirement(name = "bearerAuth"))
  public List<UserDto> getFriends() {
    UserEntity userEntity = authService.getCurrentUser();

    List<UserDto> res = userService.getFriends(userEntity.id);
    return res;
  }

  @GetMapping("/friends/sent")
  @Operation(summary = "Get all user who I've made friend request", security = @SecurityRequirement(name = "bearerAuth"))
  public List<UserDto> getSentRequest() {
    UserEntity userEntity = authService.getCurrentUser();
    List<UserDto> res = userService.getSentRequestFriends(userEntity.id);
    return res;
  }

  @GetMapping("/friends/received")
  @Operation(summary = "Get all user who's made friend request to me", security = @SecurityRequirement(name = "bearerAuth"))
  public List<UserDto> getReceivedRquest() {
    UserEntity userEntity = authService.getCurrentUser();
    List<UserDto> res = userService.getReceivedRequestFriends(userEntity.id);
    return res;
  }
}
