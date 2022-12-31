package com.example.restapi.controller;

import com.example.restapi.model.dto.ReportDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.Booking;
import com.example.restapi.model.entity.Orders;
import com.example.restapi.model.mapper.BookingMapper;
import com.example.restapi.model.mapper.OrdersMapper;
import com.example.restapi.service.ReportService;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.ReportValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private ReportValidator reportValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @PostMapping("/order")
    public ResponseEntity<RestReponseDTO> getReportOrder(@RequestBody ReportDTO reportDTO, BindingResult bindingResult) {
        RestReponseDTO restResponse = new RestReponseDTO();

        reportValidator.validate(reportDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restResponse.fail();
        }

        List<Orders> ordersList = reportService.reportOrder(reportDTO);
        if (ordersList == null) {
            restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }
        restResponse.ok(ordersMapper.toListDTO(ordersList));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/booking")
    public ResponseEntity<RestReponseDTO> getReportBooking(@RequestBody ReportDTO reportDTO, BindingResult bindingResult) {
        RestReponseDTO restResponse = new RestReponseDTO();

        List<Booking> bookingList = reportService.reportBooking(reportDTO);

        if (bookingList == null) {
            restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }
        restResponse.ok(bookingMapper.toListDTO(bookingList));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

}
