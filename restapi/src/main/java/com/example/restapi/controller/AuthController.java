package com.example.restapi.controller;

import com.example.restapi.model.dto.*;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.EmailService;
import com.example.restapi.service.impl.EmailServiceImpl;
import com.example.restapi.utils.DateUtil;
import com.example.restapi.utils.RandomUtil;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.ForgotPasswordValidator;
import com.example.restapi.validator.RegisterValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AccountService accountService;

    @Autowired
    EmailService emailService;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    RegisterValidator registerValidator;

    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    ForgotPasswordValidator forgotPasswordValidator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RestReponseDTO> register(@RequestBody RegisterDTO registerDTO, BindingResult bindingResult) {
        RestReponseDTO restReponse = new RestReponseDTO();

        // validator
        registerValidator.validate(registerDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restReponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }

        // save
        Account account = accountService.register(registerDTO);
        if (account != null && account.getId() > 0) {
            restReponse.ok(accountMapper.toDTO(account));
        } else {
            restReponse.ok();
        }

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @PostMapping(value = "/forgot-pass")
    public ResponseEntity<RestReponseDTO> forgotPass(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        RestReponseDTO restReponse = new RestReponseDTO();

        // validator
        forgotPasswordValidator.validate(accountDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restReponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }

        Account account = accountService.findByEmail(accountDTO.getEmail());

        // send mail
        EmailAccountDTO emailDTO = new EmailAccountDTO();
        emailDTO.setId(account.getId());
        emailDTO.setFullname(account.getFullName());
        emailDTO.setEmail(accountDTO.getEmail());
        emailService.sendEmailForFogotPassword(emailDTO);

        restReponse.ok();
        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @GetMapping("/reset-pass")
    public ResponseEntity<RestReponseDTO> resetPassword(@RequestParam String ref) {
        RestReponseDTO restReponse = new RestReponseDTO();
        try {
            AccountDTO accountDTO = new AccountDTO();

            String result = EmailServiceImpl.decoderBase64ToString(ref);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                MailDTO mailDTO = objectMapper.readValue(result, MailDTO.class);

                Date currentTime = new Date();
                Date time = DateUtil.convertStringToDate(mailDTO.getTime(), "HH:mm:ss dd-MM-yyyy");

                // adding 30 mins to the time
                Date afterAdding30Mins = new Date(time.getTime() + (30 * 60 * 1000));

                LinkedHashMap<String, String> linkedHashMap = ((LinkedHashMap) mailDTO.getData());

                Long id = Long.parseLong(String.valueOf(linkedHashMap.get("id")));

                Account account = accountService.findById(id);

                System.out.println(mailDTO);

                if (account != null) {
                    accountDTO = accountMapper.toDTO(account);
                    if (!currentTime.after(afterAdding30Mins)) {
                        // password
                        String newPassword = RandomUtil.generatePassword(6);
                        String encodedPassword = passwordEncoder.encode(newPassword);
                        account.setPassword(encodedPassword);

                        // save
                        accountService.save(account);

                        // send mail
                        EmailAccountDTO emailDTO = new EmailAccountDTO();
                        emailDTO.setId(account.getId());
                        emailDTO.setFullname(account.getFullName());
                        emailDTO.setPassword(newPassword);
                        emailDTO.setEmail(account.getEmail());
                        emailService.sendEmailForResetPassword(emailDTO);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            restReponse.ok();
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        } catch (Exception ex) {
            restReponse.fail();
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }
    }

}
