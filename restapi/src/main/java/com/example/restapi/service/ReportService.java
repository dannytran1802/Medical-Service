package com.example.restapi.service;

import com.example.restapi.model.dto.ReportDTO;
import com.example.restapi.model.entity.Booking;
import com.example.restapi.model.entity.Orders;

import java.util.List;

public interface ReportService {

    List<Orders> reportOrder(ReportDTO reportDTO);

    List<Booking> reportBooking(ReportDTO reportDTO);

}
