package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrdersDTO {

    private long id;
    private String progress;
    private String address;
    private String totalCost;
    private boolean status;

    private long accountId;
    private AccountDTO accountDTO;

    private long pharmacyId;
    private PharmacyDTO pharmacyDTO;

    private List<OrderDetailsDTO> orderDetails;

    protected String createdOn;
    protected String updatedOn;

}
