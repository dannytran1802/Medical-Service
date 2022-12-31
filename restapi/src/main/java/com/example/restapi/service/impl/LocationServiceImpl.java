package com.example.restapi.service.impl;

import com.example.restapi.model.entity.Location;
import com.example.restapi.repository.LocationRepository;
import com.example.restapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location findById(long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location findByAccountId(long accountId) {
        return locationRepository.findByAccountId(accountId).orElse(null);
    }

}
