package com.example.restapi.service;

import com.example.restapi.model.entity.Pharmacy;

import java.util.List;

public interface PharmacyService {
    List<Pharmacy> findAll();

    Pharmacy findById(long id);

    Pharmacy findByAccountId(long id);

    Pharmacy save(Pharmacy pharmacy);


}
