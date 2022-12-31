package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BookingDTO {

    private long id;
    private AmbulanceDTO ambulanceDTO;
    private AccountDTO accountDTO;
    private String note;
    private String progress;
    private String timeOrder;
    private boolean status;

    private long ambulanceId;
    private long accountId;

    private String uuid;
    private long historyId;

    protected String createdOn;
    protected String updatedOn;

    private double longitude;
    private double latitude;

}
