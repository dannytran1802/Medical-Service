package com.example.restapi.validator;

import com.example.restapi.model.dto.ProfileDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.service.AccountService;
import com.example.restapi.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProfileValidator implements Validator {

    @Autowired
    AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return ProfileDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProfileDTO profileDTO = (ProfileDTO) target;

        Account account = accountService.findById(profileDTO.getAccountId());
        if (account == null) {
            errors.rejectValue("accountId", "account.accountId.notexist",
                    "account.accountId.notexist");
        }

        // verify fullName
        if (StringUtils.isEmpty(profileDTO.getFullName())) {
            errors.rejectValue("fullName", "account.fullName.blank",
                    "account.fullName.blank");
        }

        // verify email
        if (StringUtils.isEmpty(profileDTO.getEmail())) {
            errors.rejectValue("email", "account.email.blank",
                    "account.email.blank");
        } else {
            if (!ValidatorUtil.checkEmail(profileDTO.getEmail())) {
                errors.rejectValue("email", "account.email.invalid",
                        "account.email.invalid");
            } else {
                account = accountService.findByEmail(profileDTO.getEmail());
                if (account != null && account.getId() != profileDTO.getAccountId()) {
                    errors.rejectValue("email", "account.email.exist", "account.email.exist");
                }
            }
        }

    }

}
