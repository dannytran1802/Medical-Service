package com.example.mobileapp.itf;

import com.example.mobileapp.model.Booking;

import java.util.List;

public interface BookingInterface {

    void onBookingPending();

    void onListBooking(List<Booking> bookingList);

    void onError(List<String> errors);

}
