package com.example.restapi.validator;

import com.example.restapi.model.dto.BookingDTO;
import com.example.restapi.service.BookingService;
import com.example.restapi.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class BookingValidator implements Validator {
    @Autowired
    BookingService bookingService;

    @Override
    public boolean supports(Class<?> aClass) {
        return BookingDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookingDTO bookingDTO = (BookingDTO) target;

        if (bookingDTO.getTimeOrder() == null || bookingDTO.getTimeOrder().trim().isEmpty()) {
            errors.rejectValue("timeOrder", "booking.timeOrder.blank",
                    "booking.timeOrder.blank");
        } else {
            //check timeBooking >= now
            Date date = DateUtil.convertStringToDate(bookingDTO.getTimeOrder(), "MM/dd/yyyy hh:mm a");
            boolean checkDate = DateUtil.compareDate(date);
            if (checkDate == false) {
                errors.rejectValue("timeOrder", "booking.timeOrder.invalid",
                        "booking.timeOrder.invalid");
            }
        }

        if (bookingDTO.getAmbulanceId() == 0){
            errors.rejectValue("ambulanceId", "booking.ambulanceId.blank",
                    "booking.ambulanceId.blank");
        }

        if (bookingDTO.getAccountId() == 0){
            errors.rejectValue("accountId", "booking.accountId.blank",
                    "booking.accountId.blank");
        }
        }
    }

