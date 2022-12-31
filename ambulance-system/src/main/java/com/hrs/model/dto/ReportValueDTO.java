package com.hrs.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportValueDTO {

    private int orderTotal;
    private int orderPending;
    private int orderApproved;
    private int orderCancel;
    private String totalPrice;

}
