package com.hrs.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private long id;
    private long pharmacyId;
    private String name;
    private double price;
    private String avatar;
    private boolean status;

}
