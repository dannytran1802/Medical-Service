package com.example.restapi.model.mapper.impl;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.AmbulanceDTO;
import com.example.restapi.model.dto.BookingDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.model.entity.Booking;
import com.example.restapi.model.mapper.BookingMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.AmbulanceService;
import com.example.restapi.service.BookingService;
import com.example.restapi.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingMapperImpl implements BookingMapper {

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BookingService bookingService;

    @Override
    public BookingDTO toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId( booking.getId() );
        bookingDTO.setProgress(booking.getProgress());
        bookingDTO.setNote(booking.getNote());
        if (booking.getTimeOrder() != null) {
            bookingDTO.setTimeOrder(DateUtil.convertDateToString(booking.getTimeOrder(), "MM/dd/yyyy h:mm a"));
        }
        bookingDTO.setStatus(booking.isStatus());
        bookingDTO.setCreatedOn(DateUtil.convertDateToString(booking.getCreatedOn(), "dd/MM/yyyy"));

        AccountDTO accountDTO = new AccountDTO();
        Account account = booking.getAccount();
        if (account != null) {
            accountDTO.setId(account.getId());
            accountDTO.setFullName(account.getFullName());
            accountDTO.setEmail(account.getEmail());
            bookingDTO.setAccountDTO(accountDTO);
            bookingDTO.setAccountId(account.getId());
        }

        AmbulanceDTO ambulanceDTO = new AmbulanceDTO();
        Ambulance ambulance = booking.getAmbulance();
        if (ambulance != null) {
            ambulanceDTO.setId(ambulance.getId());
            ambulanceDTO.setNumberPlate(ambulance.getNumberPlate());
            ambulanceDTO.setStatus(ambulance.isStatus());
            //set ambulanceAccountDTO
            Account ambulanceAccount = ambulance.getAccount();
            AccountDTO ambulanceAccountDTO = new AccountDTO();
            ambulanceAccountDTO.setFullName(ambulanceAccount.getFullName());
            ambulanceAccountDTO.setId(ambulanceAccount.getId());
            ambulanceDTO.setAccountDTO(ambulanceAccountDTO);

            bookingDTO.setAmbulanceDTO(ambulanceDTO);
            bookingDTO.setAmbulanceId(ambulance.getId());
        }
        return bookingDTO;
    }

    @Override
    public List<BookingDTO> toListDTO(List<Booking> bookings) {
        if (bookings == null) {
            return null;
        }
        List<BookingDTO> list = new ArrayList<>(bookings.size());
        for (Booking booking : bookings) {
            BookingDTO bookingDTO = toDTO(booking);
            if (bookingDTO != null) {
                list.add(bookingDTO);
            }
        }

        return list;
    }

    @Override
    public Booking toEntity(BookingDTO bookingDTO) {
        Booking booking = bookingService.findById(bookingDTO.getId());

        if (booking == null){
            booking = new Booking();
        }
        if (!StringUtils.isEmpty(bookingDTO.getTimeOrder())) {
            booking.setTimeOrder(DateUtil.convertStringToDate(bookingDTO.getTimeOrder(), "MM/dd/yyyy h:mm a"));
        }
        booking.setStatus(bookingDTO.isStatus());
        booking.setProgress(bookingDTO.getProgress());
        booking.setAccount(accountService.findById(bookingDTO.getAccountId()));

        return booking;
    }
}
