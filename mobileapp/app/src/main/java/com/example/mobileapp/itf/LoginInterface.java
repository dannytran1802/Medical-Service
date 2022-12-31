package com.example.mobileapp.itf;

import com.example.mobileapp.dto.AuthDTO;

public interface LoginInterface {

    void loginSuccess(AuthDTO authDTO);

    void loginError(String error);

}
