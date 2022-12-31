package com.example.restapi.controller;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.EmailAccountDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.service.*;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.AccountChangePasswordValidator;
import com.example.restapi.validator.AccountFormValidator;
import com.example.restapi.validator.AccountValidator;
import com.example.restapi.validator.ForgotPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private AccountFormValidator accountFormValidator;

    @Autowired
    private AccountChangePasswordValidator accountChangePasswordValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ForgotPasswordValidator forgotPasswordValidator;

    @GetMapping("/")
    public ResponseEntity<RestReponseDTO> getList() {
        RestReponseDTO restResponse = new RestReponseDTO();
        List<Account> accounts = accountService.findAll();
        restResponse.ok(accounts);
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> getAccountById(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();
        Account account = accountService.findById(id);
        restResponse.ok(accountMapper.toDTO(account));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<RestReponseDTO> updateAccount(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        RestReponseDTO restResponse = new RestReponseDTO();
        Account account = accountService.findById(accountDTO.getId());

        // validator
        accountFormValidator.validate(accountDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }
        if (account != null) {
            account = accountMapper.toEntity(account, accountDTO);

            accountService.save(account);
            restResponse.ok(account);
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
        }
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<RestReponseDTO> changePassword(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        RestReponseDTO restResponse = new RestReponseDTO();
        Account account = accountService.findById(accountDTO.getId());

        // validator
        accountChangePasswordValidator.validate(accountDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }
        if (account != null) {
//            String encodedPassword = passwordEncoder.encode(accountDTO.getNewPassword());
//            account.setPassword(encodedPassword);

            accountService.save(account);

            restResponse.ok(account);
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
        }
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<RestReponseDTO> save(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        RestReponseDTO restReponse = new RestReponseDTO();

        // validator
        accountValidator.validate(accountDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restReponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }

        Account account = null;
        if (!ObjectUtils.isEmpty(accountDTO) && accountDTO.getId() != 0) {
            account = accountService.findById(accountDTO.getId());
            //set value update
            account.setFullName(accountDTO.getFullName());
            String email = accountDTO.getEmail();
            account.setEmail(email);
            account.setUsername(email);
            account.setRole(roleService.findById(accountDTO.getRoleId()));
            account.setStatus(accountDTO.isStatus());
        } else {
            account = accountMapper.toEntity(account, accountDTO);
            account.setPassword("{bcrypt}$2a$10$r.IxbZKfVs2U6j57WYmL/.KnejrJ6Ff7eZ80za2rhhFfLmRaobWle");
        }
        // save
        Account accountResult = accountService.save(account);
        if (accountResult.getRole().getId() == 3) {
            Pharmacy pharmacy = new Pharmacy();
            pharmacy.setAccount(accountResult);
            pharmacy.setName(accountDTO.getPharmacyName());
            pharmacyService.save(pharmacy);

        } else if (accountResult.getRole().getId() == 4) {
            Ambulance ambulance = new Ambulance();
            ambulance.setAccount(accountResult);
            ambulance.setName(accountDTO.getAmbulanceName());
            ambulanceService.save(ambulance);
        }

        restReponse.ok(accountMapper.toDTO(account));

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<RestReponseDTO> forgot(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        RestReponseDTO restReponse = new RestReponseDTO();
        forgotPasswordValidator.validate(accountDTO, bindingResult);

        String email = accountDTO.getEmail();

        Account account = accountService.findByEmail(accountDTO.getEmail());
        if (account == null) {
            bindingResult.rejectValue("email", "account.email.notExists",
                    "account.email.notExists");
        } else {

            // send mail
            EmailAccountDTO emailDTO = new EmailAccountDTO();
            emailDTO.setId(account.getId());
            emailDTO.setFullname(account.getFullName());
            emailDTO.setEmail(account.getEmail());
            emailDTO.setPassword("12345678");

            emailService.sendEmailForResetPassword(emailDTO);
            bindingResult.rejectValue("email", "account.email.success",
                    "account.email.success");
        }

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<RestReponseDTO> updateToken(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        RestReponseDTO restResponse = new RestReponseDTO();
        Account account = accountService.findById(accountDTO.getId());

        if (account == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
        } else {
            account.setToken(accountDTO.getToken());
            accountService.save(account);

            restResponse.ok(accountMapper.toEntity(account, accountDTO));
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
        }
    }

}
