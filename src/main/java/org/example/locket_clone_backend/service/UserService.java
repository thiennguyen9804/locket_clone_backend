package org.example.locket_clone_backend.service;

import java.util.List;

import org.example.locket_clone_backend.domain.dto.UserDto;

public interface UserService {
	public void requestFriend(UserDto from, UserDto to);
	public void acceptFriend(UserDto from, UserDto to);
	public List<UserDto> getFriends(Long id);
	public List<UserDto> getSentRequestFriends(Long id);
	public List<UserDto> getReceivedRequestFriends(Long id);
}
