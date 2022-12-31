package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountResponse {
    public int version;
    public String createdOn;
    public String updatedOn;
    public int id;
    public RoleResponse role;
    public String fullName;
    public String email;
    public String username;
    public String password;
    public String phone;
    public String address;
    public boolean status;

    //custom
    public int roleId;

}
