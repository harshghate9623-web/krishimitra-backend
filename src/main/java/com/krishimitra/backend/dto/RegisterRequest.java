package com.krishimitra.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String phone;
    private String password;

    private List<FarmRequest> farms;
}