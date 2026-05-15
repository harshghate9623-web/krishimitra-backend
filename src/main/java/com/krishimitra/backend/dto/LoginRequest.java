package com.krishimitra.backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;   // email OR phone
    private String password;
}