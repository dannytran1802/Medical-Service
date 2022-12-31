package com.example.restapi.controller;

import com.example.restapi.model.dto.BookingDTO;
import com.example.restapi.model.dto.NotificationRequestDTO;
import com.example.restapi.model.dto.OrdersDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.*;
import com.example.restapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    OrdersService ordersService;

    @Autowired
    LocationService locationService;

    @Autowired
    BookingService bookingService;

    @Autowired
    AccountService accountService;

    @Autowired
    AmbulanceService ambulanceService;

    @Autowired
    HistoryService historyService;

    @Autowired
    NotificationService notificationService;

    @PostMapping("/order")
    public ResponseEntity<RestReponseDTO> saveOrder(@RequestBody OrdersDTO ordersDTO, BindingResult bindingResult){
        RestReponseDTO restResponse = new RestReponseDTO();

        ordersService.save(ordersDTO);

        restResponse.ok();
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/booking/ambulance")
    public ResponseEntity<RestReponseDTO> findBooking(@RequestBody BookingDTO bookingDTO, BindingResult bindingResult){
        RestReponseDTO restResponse = new RestReponseDTO();

        Account ambulance = null;

        List<Location> locationList = locationService.findAll();
        if (locationList != null) {
            for (Location location : locationList) {
                if (location.isStatus()) {
                    List<Booking> bookingList = bookingService.findAllByAccountProgress(location.getAccount().getId(), "PENDING");
                    if (bookingList == null || bookingList.isEmpty()) {
                        Ambulance ambu = ambulanceService.findByAccount(location.getAccount());
                        if (ambu != null) {
                            List<History> historyList = historyService.findByAmbulanceAndTime(ambu.getId(), bookingDTO.getUuid());
                            if (historyList == null || historyList.isEmpty()) {
                                ambulance = location.getAccount();
                                break;
                            } else {
                                for (History history : historyList) {
                                    if (history != null && !history.getProgress().equalsIgnoreCase("PENDING")
                                            && !history.getProgress().equalsIgnoreCase("CANCELED")) {
                                        ambulance = location.getAccount();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (ambulance != null) {
            History history = new History();
            history.setAmbulance(ambulanceService.findByAccount(ambulance));
            history.setAccount(accountService.findById(bookingDTO.getAccountId()));
            history.setNote(bookingDTO.getNote());
            history.setProgress("PENDING");
            history.setCode(bookingDTO.getUuid());
            history.setStatus(true);
            historyService.save(history);

            UUID uuid = UUID.randomUUID();

            // send noti
            NotificationRequestDTO notificationRequestDto = new NotificationRequestDTO();
            notificationRequestDto.setTarget(ambulance.getToken());
            notificationRequestDto.setTitle(uuid + "||BOOKING||" + String.valueOf(history.getId()) );
            notificationRequestDto.setBody("You have a booking from " + bookingDTO.getNote().toUpperCase() + ", do you agree?");
            notificationService.sendPnsToDevice(notificationRequestDto);
        }

        restResponse.ok();
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/booking/update")
    public ResponseEntity<RestReponseDTO> saveBooking(@RequestBody BookingDTO bookingDTO, BindingResult bindingResult){
        RestReponseDTO restResponse = new RestReponseDTO();

        History history = historyService.findById(bookingDTO.getHistoryId());
        if (history == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
        }

        history.setProgress(bookingDTO.getProgress());
        historyService.save(history);

        Account account = history.getAccount();
        Ambulance ambulance = history.getAmbulance();

        String progress = "PENDING";
        if (bookingDTO.getProgress().equalsIgnoreCase("COMPLETED")) {
            progress = "COMPLETED";
        }
        if (bookingDTO.getProgress().equalsIgnoreCase("APPROVED")) {
            progress = "APPROVED";
        }

        if (progress != "CANCELED") {
            Booking booking = new Booking();
            booking.setTimeOrder(new Date());
            booking.setNote(history.getNote());
            booking.setProgress(progress);
            booking.setAmbulance(ambulance);
            booking.setAccount(account);
            booking.setStatus(true);
            bookingService.save(booking);

            restResponse.ok(booking);
        } else {
            restResponse.ok();
        }

        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

}
