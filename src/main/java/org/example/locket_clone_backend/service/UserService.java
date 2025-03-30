package org.example.locket_clone_backend.service;

import org.example.locket_clone_backend.domain.dto.UserDto;

public interface UserService {
	public void requestFriend(UserDto from, UserDto to);
	public void acceptFriend(UserDto from, UserDto to);
}
