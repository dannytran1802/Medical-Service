package com.example.restapi.service.impl;

import com.example.restapi.model.entity.MessageManager;
import com.example.restapi.repository.MessageManagerRepository;
import com.example.restapi.service.MessageManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageManagerServiceImpl implements MessageManagerService {

    @Autowired
    private MessageManagerRepository messageRepository;

    @Override
    public List<MessageManager> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public MessageManager findById(long id) {
        return messageRepository.findById(id).orElse(null);
    }

    @Override
    public MessageManager save(MessageManager message) {
        return messageRepository.save(message);
    }

    @Override
    public MessageManager findByTitle(String title) {
        return messageRepository.findByTitle(title).orElse(null);
    }

}
