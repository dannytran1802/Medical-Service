package com.example.restapi.service.impl;

import com.example.restapi.model.entity.History;
import com.example.restapi.repository.HistoryRepository;
import com.example.restapi.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    @Transactional
    public History save(History history) {
        return historyRepository.save(history);
    }

    @Override
    public History findByAmbulance(long ambulanceId, String code) {
        return historyRepository.findByAmbulance(ambulanceId, code).orElse(null);
    }

    @Override
    public List<History> findByAmbulanceAndTime(long ambulanceId, String code) {
        return historyRepository.findByAmbulanceAndTime(ambulanceId, code);
    }

    @Override
    public History findById(long id) {
        return historyRepository.findById(id).orElse(null);
    }

}
