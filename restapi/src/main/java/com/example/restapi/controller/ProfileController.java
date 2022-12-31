package com.example.restapi.controller;

import com.example.restapi.model.dto.*;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.service.*;
import com.example.restapi.service.impl.EmailServiceImpl;
import com.example.restapi.utils.DateUtil;
import com.example.restapi.utils.RandomUtil;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class ProfileController {

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

    @GetMapping("/profile/{id}")
    public ResponseEntity<RestReponseDTO> findById(@PathVariable(value = "id") String id) {
        RestReponseDTO restReponse = new RestReponseDTO();

        Account account = accountService.findById(Long.parseLong(id));

        if (account == null) {
            restReponse.fail();
        } else {
            restReponse.ok(accountMapper.toDTO(account));
        }

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<RestReponseDTO> updateProfile(@RequestBody ProfileDTO profileDTO, BindingResult bindingResult) {
        RestReponseDTO restReponse = new RestReponseDTO();

        // validator
        profileValidator.validate(profileDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restReponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }

        // save
        Account account = accountService.findById(profileDTO.getAccountId());
        if(!ObjectUtils.isEmpty(account)) {
            account.setFullName(profileDTO.getFullName());
            account.setUsername(profileDTO.getEmail());
            account.setEmail(profileDTO.getEmail());
            account.setPhone(profileDTO.getPhone());
            account.setAddress(profileDTO.getAddress());
            account.setContact(profileDTO.getContact());
            accountService.save(account);

            // ambulance
            Ambulance ambulance = ambulanceService.findByAccount(account);
            if (!ObjectUtils.isEmpty(ambulance)) {
                ambulance.setNumberPlate(profileDTO.getNumberPlate());
                ambulance.setName(profileDTO.getFullName());
                ambulanceService.save(ambulance);
            }

            // pharmacy
            Pharmacy pharmacy = pharmacyService.findByAccountId(account.getId());
            if (!ObjectUtils.isEmpty(pharmacy)) {
                pharmacy.setName(profileDTO.getFullName());
                pharmacy.setAddress(profileDTO.getAddress());
                pharmacyService.save(pharmacy);
            }
        }

        AccountDTO accountDTO = accountMapper.toDTO(account);
        accountDTO.setNumberPlate(profileDTO.getNumberPlate());
        restReponse.ok(accountDTO);

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

}
