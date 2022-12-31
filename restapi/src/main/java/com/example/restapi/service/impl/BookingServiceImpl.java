package com.example.restapi.service.impl;

import com.example.restapi.model.entity.Booking;
import com.example.restapi.repository.BookingRepository;
import com.example.restapi.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findAllByProgress(String progress) {
        return bookingRepository.findByProgress(progress);
    }

    @Override
    public List<Booking> findAllByAccountProgress(long accountId, String progress) {
        return bookingRepository.findAllByAccountProgress(accountId, progress);
    }

    @Override
    public List<Booking> findAllByAmbulance(long ambulanceId) {
        return bookingRepository.findAllByAmbulance(ambulanceId);
    }

    @Override
    public List<Booking> findAllByAmbulanceAndProgress(long ambulanceId, String progress) {
        return bookingRepository.findAllByAmbulanceAndProgress(ambulanceId, progress);
    }

}
