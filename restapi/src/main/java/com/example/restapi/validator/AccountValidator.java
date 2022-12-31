package com.example.restapi.validator;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountValidator implements Validator {
    @Autowired
    AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return AccountDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDTO accountDTO = (AccountDTO) target;
        // validator fullName
        if (StringUtils.isEmpty(accountDTO.getFullName())) {
            errors.rejectValue("fullName", "account.fullName.blank",
                    "account.fullName.blank");
        }

        // validator email
        if (StringUtils.isEmpty(accountDTO.getEmail())) {
            errors.rejectValue("email", "account.email.blank",
                    "account.email.blank");
        } else {
            Account account = accountService.findByEmail(accountDTO.getEmail());
            if (account != null && accountDTO.getId() != account.getId()) {
                errors.rejectValue("email", "account.email.exist", "account.email.exist");
            }
        }

        // validator password
//        if (accountDTO.getId() == 0) {
//            if (StringUtils.isEmpty(accountDTO.getPassword())) {
//                errors.rejectValue("password", "account.password.blank",
//                        "account.password.blank");
//            } else if (accountDTO.getPassword().length() < 8) {
//                errors.rejectValue("password", "account.password.invalid",
//                        "account.password.invalid");
//            }
//        }
    }
}
