package com.example.restapi.model.mapper.impl;

import com.example.restapi.model.dto.MessageManagerDTO;
import com.example.restapi.model.entity.MessageManager;
import com.example.restapi.model.mapper.MessageManagerMapper;
import com.example.restapi.service.MessageManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageManagerMapperImpl implements MessageManagerMapper {

    @Autowired
    private MessageManagerService messageService;

    @Override
    public MessageManagerDTO toDTO(MessageManager message) {
        if (message == null) {
            return null;
        }

        MessageManagerDTO messageDTO = new MessageManagerDTO();
        messageDTO.setId(message.getId());
        messageDTO.setTitle(message.getTitle());
        messageDTO.setContent(message.getContent());
        messageDTO.setStatus(message.isStatus());

        return messageDTO;
    }

    @Override
    public List<MessageManagerDTO> toListDTO(List<MessageManager> messages) {
        if (messages == null) {
            return null;
        }

        List<MessageManagerDTO> list = new ArrayList<>(messages.size());
        for (MessageManager message : messages) {
            MessageManagerDTO messageDTO = toDTO(message);
            if (messageDTO != null) {
                list.add(messageDTO);
            }
        }

        return list;
    }

    @Override
    public MessageManager toEntity(MessageManagerDTO messageDTO) {

        if (messageDTO == null) {
            return null;
        }
        MessageManager message = new MessageManager();
        if (message == null) {
            message = new MessageManager();
        }

        message.setTitle(messageDTO.getTitle());
        message.setContent(messageDTO.getContent());
        message.setStatus(messageDTO.isStatus());

        return message;
    }
}
