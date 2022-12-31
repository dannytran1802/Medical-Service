package com.example.restapi.service.impl;

import com.example.restapi.model.dto.ReportDTO;
import com.example.restapi.model.entity.Booking;
import com.example.restapi.model.entity.Orders;
import com.example.restapi.repository.BookingRepository;
import com.example.restapi.repository.OrdersRepository;
import com.example.restapi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<Orders> reportOrder(ReportDTO reportDTO) {
        String startDate = reportDTO.getStartDate() + " 00:00:00";
        String endDate = reportDTO.getEndDate() + " 23:59:59";
        return ordersRepository.findReportOrder(startDate, endDate);
    }

    @Override
    public List<Booking> reportBooking(ReportDTO reportDTO) {
        String startDate = reportDTO.getStartDate() + " 00:00:00";
        String endDate = reportDTO.getEndDate() + " 23:59:59";
        return bookingRepository.findReportBooking(startDate, endDate);
    }

}
