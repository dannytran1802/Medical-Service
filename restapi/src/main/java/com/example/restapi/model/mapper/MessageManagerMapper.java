package com.example.restapi.model.mapper;

import com.example.restapi.model.dto.MessageManagerDTO;
import com.example.restapi.model.entity.MessageManager;

import java.util.List;

public interface MessageManagerMapper {

    // Map Entity to DTO
    MessageManagerDTO toDTO(MessageManager message);

    List<MessageManagerDTO> toListDTO(List<MessageManager> messages);

    // Map DTO to Entity
    MessageManager toEntity(MessageManagerDTO messageDTO);

}
