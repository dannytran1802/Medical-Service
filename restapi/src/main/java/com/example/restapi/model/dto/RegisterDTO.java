package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDTO {

    private String fullName;
    private String pharmacy;
    private String ambulance;
    private String username;
    private String password;
    private String roleName;
    private String otp;

}
