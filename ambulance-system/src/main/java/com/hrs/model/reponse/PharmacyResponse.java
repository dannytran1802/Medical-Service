package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PharmacyResponse {

    public int version;
    public String createdOn;
    public String updatedOn;

    private int id;
    private int accountId;
    private AccountResponse accountDTO;
    private String name;
    private String address;
    private boolean status;

}
