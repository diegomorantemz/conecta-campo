package com.conectacampo.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String dni;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String role;
    private String department;
    private String province;
    private String district;
    private Boolean enabled;
    private LocalDateTime createdAt;
}