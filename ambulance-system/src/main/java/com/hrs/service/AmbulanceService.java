package com.hrs.service;

import com.hrs.model.reponse.AmbulanceResponse;

import java.util.List;

public interface AmbulanceService {

    List<AmbulanceResponse> getList(String token);

    AmbulanceResponse getById(String token, long id);



}
