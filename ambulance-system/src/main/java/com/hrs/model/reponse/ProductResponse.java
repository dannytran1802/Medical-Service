package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    public int version;
    public String createdOn;
    public String updatedOn;

    private int id;
    private int pharmacyId;
    private PharmacyResponse pharmacyDTO;
    private String name;
    private String price;
    private String avatar;
    private boolean status;

}
