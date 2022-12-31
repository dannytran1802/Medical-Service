package com.hrs.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PharmacyDTO {

    private long id;
    private long accountId;
    private String name;
    private String address;
    private boolean status;

}
