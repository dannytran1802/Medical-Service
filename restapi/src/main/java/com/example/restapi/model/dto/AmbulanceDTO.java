package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AmbulanceDTO {

    private long id;
    private AccountDTO accountDTO;
    @NotEmpty
    private String name;
    private String numberPlate;
    private boolean status;
    private long accountId;

}
