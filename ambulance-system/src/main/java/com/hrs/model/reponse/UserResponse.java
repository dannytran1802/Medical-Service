package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponse {
    public String access_token;
    public String token_type;
    public String refresh_token;
    public int expires_in;
    public String scope;
    public int accountId;
    public String username;
    public String email;
    public String fullName;
    public String phone;
    public String role;
    public String jti;
}
