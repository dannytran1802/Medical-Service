package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileDTO {

    private long accountId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String numberPlate;
    private String contact;
    private String oldPass;
    private String newPass;

}
