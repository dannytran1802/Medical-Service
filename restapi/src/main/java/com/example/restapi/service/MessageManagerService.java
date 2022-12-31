package com.example.restapi.service;

import com.example.restapi.model.entity.MessageManager;

import java.util.List;

public interface MessageManagerService {
    List<MessageManager> findAll();

    MessageManager findById(long id);

    MessageManager save(MessageManager message);

    MessageManager findByTitle(String title);

}
