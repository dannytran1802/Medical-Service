package com.hrs.service;

import com.hrs.model.reponse.PharmacyResponse;

import java.util.List;

public interface PharmacyService {

    List<PharmacyResponse> getList(String token);

    PharmacyResponse getById(String token, long id);
    
}
