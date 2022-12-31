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
public class ForgotPasswordValidator implements Validator {

    @Autowired
    AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return AccountDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDTO accountDTO = (AccountDTO) target;

        Account account = null;

        // validator email
        if (StringUtils.isEmpty(accountDTO.getEmail())) {
            errors.rejectValue("email", "account.email.blank",
                    "account.email.blank");
        } else {
            account = accountService.findByEmail(accountDTO.getEmail());
            if (account == null) {
                errors.rejectValue("email", "account.email.notExists",
                        "account.email.notExists");
            }
        }
    }
}
