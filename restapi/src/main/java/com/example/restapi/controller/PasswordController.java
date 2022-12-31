package com.example.restapi.controller;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.ProfileDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.AmbulanceService;
import com.example.restapi.service.EmailService;
import com.example.restapi.service.PharmacyService;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.ProfileValidator;
import com.example.restapi.validator.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class PasswordController {

    @Autowired
    AccountService accountService;

    @Autowired
    AmbulanceService ambulanceService;

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    EmailService emailService;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    RegisterValidator registerValidator;

    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    ProfileValidator profileValidator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/password")
    public ResponseEntity<RestReponseDTO> updateProfile(@RequestBody ProfileDTO profileDTO) {
        RestReponseDTO restReponse = new RestReponseDTO();

        Account account = accountService.findById(profileDTO.getAccountId());
        if (account == null) {
            restReponse.fail();
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }

        if (!passwordEncoder.matches(profileDTO.getOldPass(), account.getPassword())) {
            restReponse.fail();
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }

        // save
        if(!ObjectUtils.isEmpty(account)) {
            String encodedPassword = passwordEncoder.encode(profileDTO.getNewPass());
            account.setPassword(encodedPassword);
            accountService.save(account);
        }

        restReponse.ok();
        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

}
