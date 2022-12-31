package com.example.restapi.service;

import com.example.restapi.model.dto.RegisterDTO;
import com.example.restapi.model.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findById(long id);

    Account save(Account account);

    Account findByEmail(String email);

    Account findByPhone(String phone);

    Account register(RegisterDTO registerDTO);

}
