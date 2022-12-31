package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsDTO {

    private long id;
    private String quantity;
    private String price;
    private boolean status;

    private long productId;
    private ProductDTO productDTO;

    private long orderId;
    private OrdersDTO orderDTO;
}
