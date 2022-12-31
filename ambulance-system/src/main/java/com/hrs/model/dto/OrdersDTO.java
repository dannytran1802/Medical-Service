package com.hrs.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersDTO {

    private long id;
    private long accountId;
    private long pharmacyId;
    private String progress;
    private double totalCost;
    private boolean status;

}
