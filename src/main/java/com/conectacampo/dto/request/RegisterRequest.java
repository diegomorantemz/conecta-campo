package com.conectacampo.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String dni;
    private String name;
    private String lastname;
    private String phone;
    private String email;
    private String password;
    private String confirmPassword;
    private String department;
    private String province;
    private String district;
    private String userType; // "farmer" o "buyer"
}