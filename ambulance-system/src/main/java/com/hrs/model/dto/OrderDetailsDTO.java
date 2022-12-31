package com.hrs.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsDTO {

    private long id;
    private long productId;
    private long ordersId;
    private int quantity;
    private double price;
    private boolean status;

}
