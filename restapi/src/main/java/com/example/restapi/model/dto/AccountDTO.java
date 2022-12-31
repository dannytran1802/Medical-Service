package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    private long id;
    private String fullName;
    private String pharmacyName;
    private String ambulanceName;
    private String email;
    private String username;
    private String password;
    private String phone;
    private String roleName;
    private String address;
    private String contact;
    private boolean status;
    private long locationId;
    private LocationDTO location;
    private long roleId;
    private RoleDTO role;
    private String numberPlate;

    private String rePassword;
    private String oldPassword;
    private String newPassword;
    private String verifyNewPassword;

    private String token;

}
