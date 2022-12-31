package com.example.restapi.model.dto;

import com.example.restapi.model.entity.Account;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PharmacyDTO {

    private long id;
    private String name;
    private String address;
    private boolean status;

    private long accountId;
    private AccountDTO accountDTO;
}
