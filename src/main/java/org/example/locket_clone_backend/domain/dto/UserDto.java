package org.example.locket_clone_backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    public Long id;
    public String name;
    public String avatarUrl;
    public String email;

    public String phoneNumber;
}
