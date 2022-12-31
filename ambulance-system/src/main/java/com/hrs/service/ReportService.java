package com.hrs.service;

import com.hrs.model.dto.ReportDTO;
import com.hrs.model.reponse.BookingResponse;
import com.hrs.model.reponse.OrdersResponse;

import java.util.List;

public interface ReportService {

    List<OrdersResponse> getReportOrders(String token, ReportDTO reportDTO);

    List<BookingResponse> getReportBooking(String token, ReportDTO reportDTO);

}
