package com.example.restapi.service.impl;

import com.example.restapi.model.entity.OTP;
import com.example.restapi.repository.OtpRepository;
import com.example.restapi.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public OTP findById(long id) {
        return otpRepository.findById(id).orElse(null);
    }

    @Override
    public OTP findByUsername(String username) {
        return otpRepository.findByUsername(username).orElse(null);
    }

    @Override
    public OTP save(OTP otp) {
        return otpRepository.save(otp);
    }

}
