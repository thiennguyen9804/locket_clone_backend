package org.example.locket_clone_backend.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SignUpReq {
    @NotNull
    @NotBlank
    public String email;
    @NotNull
    @NotBlank
    public String password;
    @NotNull
    @NotBlank
    public String name;
    @NotNull
    @NotBlank
    public String phoneNumber;
}
