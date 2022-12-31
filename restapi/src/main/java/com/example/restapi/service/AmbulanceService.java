package com.example.restapi.service;

import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Ambulance;

import java.util.List;

public interface AmbulanceService {
    List<Ambulance> findAll();

    Ambulance findById(long id);

    Ambulance save(Ambulance ambulance);

    Ambulance findByNumberPlate(String numberPlate);

    Ambulance findByAccount(Account account);

}
