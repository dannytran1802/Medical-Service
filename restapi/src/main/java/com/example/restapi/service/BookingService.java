package com.example.restapi.service;

import com.example.restapi.model.entity.Booking;
import java.util.List;

public interface BookingService {
    List<Booking> findAll();

    Booking findById(long id);

    Booking save(Booking booking);

    List<Booking> findAllByProgress(String progress);

    List<Booking> findAllByAccountProgress(long accountId, String progress);

    List<Booking> findAllByAmbulance(long ambulanceId);

    List<Booking> findAllByAmbulanceAndProgress(long ambulanceId, String progress);

}
