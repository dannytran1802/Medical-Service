package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersResponse {

    public int version;
    public String createdOn;
    public String updatedOn;

    private int id;
    private String progress;
    private String totalCost;
    private String address;
    private boolean status;

    private int accountId;
    private AccountResponse accountDTO;

    private int pharmacyId;
    private PharmacyResponse pharmacyDTO;

}
