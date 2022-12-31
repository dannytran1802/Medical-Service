package com.example.restapi.service;


import com.example.restapi.model.entity.Location;

import java.util.List;

public interface LocationService {

    Location findById(long id);

    List<Location> findAll();

    Location save(Location location);

    Location findByAccountId(long accountId);

}