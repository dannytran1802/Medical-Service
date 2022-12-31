package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {

    private long id;
    private String name;
    private String price;
    private String avatar;
    private String description;
    private boolean otc;
    private boolean status;

    private long pharmacyId;
    private PharmacyDTO pharmacy;

}
