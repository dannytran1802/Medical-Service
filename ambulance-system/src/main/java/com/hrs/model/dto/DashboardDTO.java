package com.hrs.model.dto;

import com.hrs.model.reponse.BookingResponse;
import com.hrs.model.reponse.OrdersResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DashboardDTO {

    private long totalAmbulance;

    private long totalBooking;

    private long totalPharmacy;

    private long totalOrder;

    private List<BookingResponse> bookingResponseList;

    private List<OrdersResponse> ordersResponseList;

}
