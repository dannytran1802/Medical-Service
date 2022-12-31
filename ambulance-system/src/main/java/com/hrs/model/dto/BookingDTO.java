package com.hrs.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookingDTO {
    private long id;
    private long accountId;
    private long ambulanceId;
    private String progress;
    private String timeOrder;
    private boolean status;
}
