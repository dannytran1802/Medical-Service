package com.example.restapi.service.impl;

import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.repository.PharmacyRepository;
import com.example.restapi.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Override
    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }

    @Override
    public Pharmacy findById(long id) {
        return pharmacyRepository.findById(id).orElse(null);
    }

    @Override
    public Pharmacy findByAccountId(long id) {
        return pharmacyRepository.findByAccountId(id);
    }

    @Override
    public Pharmacy save(Pharmacy pharmacy) {
        return pharmacyRepository.save(pharmacy);
    }

}
