package com.example.restapi.validator;

import com.example.restapi.model.dto.RegisterDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.OTP;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.OtpService;
import com.example.restapi.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {

    @Autowired
    AccountService accountService;

    @Autowired
    OtpService otpService;

    @Override
    public boolean supports(Class<?> aClass) {
        return RegisterDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterDTO registerDTO = (RegisterDTO) target;

        // verify otp
        if (!StringUtils.isEmpty(registerDTO.getOtp())) {
            OTP otp = otpService.findByUsername(registerDTO.getUsername());
            if (!otp.getOtp().equalsIgnoreCase(registerDTO.getOtp())) {
                errors.rejectValue("otp", "account.otp.invalid",
                        "account.otp.invalid");
            }
        }

        // verify fullName
        if (StringUtils.isEmpty(registerDTO.getFullName())) {
            errors.rejectValue("fullName", "account.fullName.blank",
                    "account.fullName.blank");
        }

        // verify username
        if (StringUtils.isEmpty(registerDTO.getUsername())) {
            errors.rejectValue("username", "account.username.blank",
                    "account.username.blank");
        } else {
            if (!ValidatorUtil.checkEmail(registerDTO.getUsername())) {
                errors.rejectValue("username", "account.email.invalid",
                        "account.email.invalid");
            } else {
                Account account = accountService.findByEmail(registerDTO.getUsername());
                if (account != null) {
                    errors.rejectValue("username", "account.username.exist", "account.username.exist");
                }
            }
        }

        // verify password
        if (StringUtils.isEmpty(registerDTO.getPassword())) {
            errors.rejectValue("password", "account.password.blank",
                    "account.password.blank");
        } else if (registerDTO.getPassword().length() < 6) {
            errors.rejectValue("password", "account.password.invalid",
                    "account.password.invalid");
        }

        // verify role
        if (StringUtils.isEmpty(registerDTO.getRoleName())) {
            errors.rejectValue("role", "account.role.blank",
                    "account.role.blank");
        } else {
//            switch (registerDTO.getRoleName().toUpperCase()) {
//                case "PHARMACY":
//                    errors.rejectValue("pharmacy", "account.pharmacy.blank",
//                            "account.pharmacy.blank");
//                    break;
//                case "AMBULANCE":
//                    errors.rejectValue("ambulance", "account.ambulance.blank",
//                            "account.ambulance.blank");
//                    break;
//                default:
//                    break;
//            }
        }
    }

}
