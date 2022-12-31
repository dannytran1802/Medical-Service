package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class LocationDTO {

    private long id;
    private AccountDTO accountDTO;
    private double longitude;
    private boolean status;
    private double latitude;

    private long accountId;

}
