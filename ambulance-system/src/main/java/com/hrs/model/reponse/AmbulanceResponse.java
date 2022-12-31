package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AmbulanceResponse {

    public int version;
    public String createdOn;
    public String updatedOn;
    public int id;
    private AccountResponse accountDTO;
    private String name;
    private String numberPlate;
    private String avatar;
    private boolean status;

    //custom
    public int accountId;

}
