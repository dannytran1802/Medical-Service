package com.example.restapi.validator;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.service.AccountService;
import com.example.restapi.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountFormValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            AccountDTO accountDTO = (AccountDTO) target;
            Account account = null;

            // verify fullName
            if (ValidatorUtil.isEmpty(accountDTO.getFullName())) {
                errors.rejectValue("fullName", "account.fullName.blank",
                        "account.fullName.blank");
            }

            // verify phone
            String phone = accountDTO.getPhone();
            if (ValidatorUtil.isEmpty(phone)) {
                errors.rejectValue("phone", "account.phone.blank",
                        "account.phone.blank");
            } else {
                if (!ValidatorUtil.checkPhone(phone)) {
                    errors.rejectValue("phone", "account.phone.format", "account.phone.format");
                } else {
                    account = accountService.findByPhone(phone);
                    if (account != null && account.getId() != accountDTO.getId()) {
                        errors.rejectValue("phone", "account.phone.exists",
                                "account.phone.exists");
                    }
                }
            }

        } catch (Exception e) {
            errors.rejectValue("msg", "account.error",
                    "account.error");
        }
    }
}
