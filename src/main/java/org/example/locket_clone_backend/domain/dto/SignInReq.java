package org.example.locket_clone_backend.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInReq {
    @NotEmpty
    public String loginInfo;
    @NotEmpty
    public String password;

}
