package com.example.restapi.validator;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountChangePasswordValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDTO accountDTO = (AccountDTO) target;
        Account account = accountService.findById(accountDTO.getId());

        // verify old password
        if (accountDTO.getOldPassword() == null || accountDTO.getOldPassword().trim().isEmpty()) {
            errors.rejectValue("oldPassword", "account.oldPassword.blank",
                    "account.oldPassword.blank");
        } else {
//            if (!passwordEncoder.matches(accountDTO.getOldPassword(), account.getPassword())) {
//                errors.rejectValue("oldPassword", "account.oldPassword.fail!",
//                        "account.oldPassword.fail");
//            }
        }

        // verify new password
        if (accountDTO.getNewPassword() == null || accountDTO.getNewPassword().trim().isEmpty()) {
            errors.rejectValue("newPassword", "account.newPassword.blank",
                    "account.newPassword.blank");
        } else {
            if (accountDTO.getNewPassword().length() < 8) {
                errors.rejectValue("newPassword", "account.newPassword.size",
                        "account.newPassword.size");
            }
        }

        // verify new password again
        if (accountDTO.getVerifyNewPassword() == null || accountDTO.getVerifyNewPassword().trim().isEmpty()) {
            errors.rejectValue("verifyNewPassword", "account.verifyNewPassword.blank",
                    "account.verifyNewPassword.blank");
        } else {
            if (!accountDTO.getNewPassword().equalsIgnoreCase(accountDTO.getVerifyNewPassword())) {
                errors.rejectValue("verifyNewPassword", "account.verifyNewPassword.match",
                        "account.verifyNewPassword.match");
            }
        }
    }
}
