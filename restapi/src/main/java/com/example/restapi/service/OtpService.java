package com.example.restapi.service;

import com.example.restapi.model.entity.OTP;

public interface OtpService {

    OTP findById(long id);

    OTP findByUsername(String username);

    OTP save (OTP otp);

}
