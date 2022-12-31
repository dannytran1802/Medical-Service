package com.example.restapi.service.impl;

import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.model.entity.Role;
import com.example.restapi.repository.AmbulanceRepository;
import com.example.restapi.repository.RoleRepository;
import com.example.restapi.service.AmbulanceService;
import com.example.restapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmbulanceServiceImpl implements AmbulanceService {

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Override
    public List<Ambulance> findAll() {
        return ambulanceRepository.findAll();
    }

    @Override
    public Ambulance findById(long id) {
        return ambulanceRepository.findById(id).orElse(null);
    }

    @Override
    public Ambulance save(Ambulance ambulance) {
        return ambulanceRepository.save(ambulance);
    }

    @Override
    public Ambulance findByNumberPlate(String numberPlate) {
        return ambulanceRepository.findByNumberPlate(numberPlate);
    }

    @Override
    public Ambulance findByAccount(Account account) {
        return ambulanceRepository.findByAccount(account);
    }

}
