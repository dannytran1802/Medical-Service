package com.example.restapi.service.impl;

import com.example.restapi.model.dto.EmailAccountDTO;
import com.example.restapi.model.dto.RegisterDTO;
import com.example.restapi.model.entity.*;
import com.example.restapi.repository.AccountRepository;
import com.example.restapi.service.*;
import com.example.restapi.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    AmbulanceService ambulanceService;

    @Autowired
    RoleService roleService;

    @Autowired
    OtpService otpService;

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;


    @Override
    public Account findById(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Account findByPhone(String phone) {
        return accountRepository.findByPhone(phone).orElse(null);
    }

    @Override
    public Account register(RegisterDTO registerDTO) {
        Account account = new Account();

        boolean isOtp = registerDTO.getOtp() != null && !registerDTO.getOtp().isEmpty();
        if (isOtp) {
            // set account
            account.setFullName(registerDTO.getFullName());
            account.setEmail(registerDTO.getUsername());
            account.setUsername(registerDTO.getUsername());
            String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
            account.setPassword(encodedPassword);
            account.setStatus(true);

            // set role
            Role role = roleService.findByName(registerDTO.getRoleName());
            account.setRole(role);

            // save account
            accountRepository.save(account);

            switch (registerDTO.getRoleName().toUpperCase()) {
                case "PHARMACY":
                    Pharmacy pharmacy = new Pharmacy();
                    pharmacy.setAccount(account);
                    pharmacy.setName(registerDTO.getFullName());
                    pharmacy.setAddress(registerDTO.getPharmacy());
                    pharmacy.setStatus(true);
                    pharmacyService.save(pharmacy);
                    break;
                case "AMBULANCE":
                    Ambulance ambulance = new Ambulance();
                    ambulance.setAccount(account);
                    ambulance.setName(registerDTO.getFullName());
                    ambulance.setNumberPlate(registerDTO.getAmbulance());
                    ambulance.setStatus(true);
                    ambulanceService.save(ambulance);
                    break;
                default:
                    break;
            }
        } else {
            // save otp
            OTP otp = otpService.findByUsername(registerDTO.getUsername());
            if (otp == null) {
                otp = new OTP();
            }
            otp.setUsername(registerDTO.getUsername());
            otp.setOtp(RandomUtil.generateNumber(4));
            otpService.save(otp);

            // send email
            EmailAccountDTO emailDTO = new EmailAccountDTO();
            emailDTO.setFullname(registerDTO.getUsername());
            emailDTO.setEmail(registerDTO.getUsername());
            emailDTO.setOtp(otp.getOtp());

            emailService.sendEmailForOtp(emailDTO);
        }

        return account;
    }

}
