package com.example.restapi.service;

import com.example.restapi.model.entity.History;

import java.util.List;

public interface HistoryService {

    History save(History history);

    History findByAmbulance(long ambulanceId, String code);

    List<History> findByAmbulanceAndTime(long ambulanceId, String code);

    History findById(long id);

}