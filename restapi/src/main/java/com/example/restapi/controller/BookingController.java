package com.example.restapi.controller;

import com.example.restapi.model.dto.BookingDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.model.entity.Booking;
import com.example.restapi.model.mapper.BookingMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.AmbulanceService;
import com.example.restapi.service.BookingService;
import com.example.restapi.utils.DateUtil;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.BookingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private BookingValidator bookingValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping("/list")
    public ResponseEntity<RestReponseDTO> getList() {
        RestReponseDTO restReponse = new RestReponseDTO();
        restReponse.ok(bookingMapper.toListDTO(bookingService.findAll()));
        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @GetMapping("/find-progress")
    public ResponseEntity<RestReponseDTO> getListProgressPending() {
        RestReponseDTO restReponse = new RestReponseDTO();
        restReponse.ok(bookingMapper.toListDTO(bookingService.findAllByProgress("PENDING")));
        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> findById(@PathVariable long id) {
        RestReponseDTO restReponse = new RestReponseDTO();

        Booking booking = bookingService.findById(id);
        if (booking == null) {
            restReponse.fail();
        } else {
            List<BookingDTO> bookingDTOList = new ArrayList<>();
            bookingDTOList.add(bookingMapper.toDTO(booking));
            restReponse.ok(bookingMapper.toDTO(booking));
        }

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<RestReponseDTO> save(@RequestBody BookingDTO bookingDTO, BindingResult bindingResult) {
        RestReponseDTO restReponse = new RestReponseDTO();

        // validator
        bookingValidator.validate(bookingDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restReponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }

        Booking booking = bookingService.findById(bookingDTO.getId());

        if (booking == null) {
            booking = new Booking();
        }

        booking.setId( bookingDTO.getId() );
        booking.setProgress( bookingDTO.getProgress() );
        booking.setAccount(accountService.findById(bookingDTO.getAccountId()));
        booking.setAmbulance(ambulanceService.findById(bookingDTO.getAmbulanceId()));
        booking.setTimeOrder(DateUtil.convertStringToDate(bookingDTO.getTimeOrder(), "MM/dd/yyyy h:mm a"));
        booking.setStatus( bookingDTO.isStatus() );


        restReponse.ok(bookingMapper.toDTO(bookingService.save(booking)));

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> delete(@PathVariable long id) {
        RestReponseDTO restReponse = new RestReponseDTO();

        Booking booking = bookingService.findById(id);
        if (booking == null) {
            restReponse.fail();
        } else {
            booking.setStatus(false);
            bookingService.save(booking);
            restReponse.ok(bookingMapper.toDTO(booking));
        }

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @GetMapping("/account/approved/{id}")
    public ResponseEntity<RestReponseDTO> findByAccountIdAndApproved(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();

        Account account = accountService.findById(id);

        if (account == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        List<Booking> bookingList = bookingService.findAllByAccountProgress(account.getId(), "APPROVED");
        if (bookingList != null && !bookingList.isEmpty()) {
            restResponse.ok(bookingMapper.toListDTO(bookingList));
        } else {
            restResponse.fail();
        }

        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<RestReponseDTO> findByAccountId(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();

        Account account = accountService.findById(id);

        if (account == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        List<Booking> bookingList = bookingService.findAllByAccountProgress(account.getId(), "APPROVED");
        if (bookingList != null && !bookingList.isEmpty()) {
            restResponse.ok(bookingMapper.toListDTO(bookingList));
        } else {
            restResponse.fail();
        }

        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/ambulance/{id}")
    public ResponseEntity<RestReponseDTO> findByAmbulanceId(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();

        Account account = accountService.findById(id);
        Ambulance ambulance = ambulanceService.findByAccount(account);

        if (ambulance == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        List<Booking> bookingList = bookingService.findAllByAmbulance(ambulance.getId());
        if (bookingList != null && !bookingList.isEmpty()) {
            restResponse.ok(bookingMapper.toListDTO(bookingList));
        } else {
            restResponse.fail();
        }

        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/ambulance/progress/{id}")
    public ResponseEntity<RestReponseDTO> findByAmbulanceIdAndProgress(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();

        Account account = accountService.findById(id);
        Ambulance ambulance = ambulanceService.findByAccount(account);

        if (ambulance == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        List<Booking> bookingList = bookingService.findAllByAmbulanceAndProgress(ambulance.getId(), "APPROVED");
        if (bookingList != null && !bookingList.isEmpty()) {
            restResponse.ok(bookingMapper.toListDTO(bookingList));
        } else {
            restResponse.fail();
        }

        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/ambulance/progress/completed/{id}")
    public ResponseEntity<RestReponseDTO> bookingCompleted(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();

        Booking booking = bookingService.findById(id);
        booking.setProgress("COMPLETED");
        bookingService.save(booking);

        restResponse.ok();

        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }


}
