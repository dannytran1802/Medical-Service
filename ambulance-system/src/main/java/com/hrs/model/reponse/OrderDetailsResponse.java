package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsResponse {

    public int version;
    public String createdOn;
    public String updatedOn;

    private int id;
    private int quantity;
    private String price;
    private boolean status;

    private int productId;
    private ProductResponse productDTO;

    private int ordersId;
    private OrdersResponse ordersDTO;

}
