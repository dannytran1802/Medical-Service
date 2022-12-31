package com.example.restapi.model.mapper;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;

import java.util.List;

public interface AccountMapper {

    // Map Entity to DTO
    AccountDTO toDTO(Account account);

    List<AccountDTO> toListDTO(List<Account> accounts);

    // Map DTO to Entity
    Account toEntity(Account account, AccountDTO accountDTO);

}
