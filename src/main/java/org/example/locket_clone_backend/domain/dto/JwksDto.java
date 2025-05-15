package org.example.locket_clone_backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwksDto {
    private String alg;
    private String kty;
    private String use;
    private String[] x5c;
    private String n;
    private String e;
    private String kid;
    private String x5t;
}
