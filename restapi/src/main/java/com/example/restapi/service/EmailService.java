package com.example.restapi.service;
import com.example.restapi.model.dto.EmailAccountDTO;

public interface EmailService {

    boolean sendEmailForFogotPassword(EmailAccountDTO emailDTO);

    boolean sendEmailForResetPassword(EmailAccountDTO emailDTO);

    boolean sendEmailForOtp(EmailAccountDTO emailDTO);

}
