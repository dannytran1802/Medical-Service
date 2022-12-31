package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookingResponse {

    public int version;
    public String createdOn;
    public String updatedOn;
    public int id;
    private AccountResponse accountDTO;
    private AmbulanceResponse ambulanceDTO;
    private String progress;
    private String timeOrder;
    private boolean status;

    //custom
    public int accountId;
    public int ambulanceId;

}
