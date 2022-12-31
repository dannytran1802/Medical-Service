package com.hrs.service;

import com.hrs.model.dto.MessageManagerDTO;
import com.hrs.model.reponse.MessageResponse;

import java.util.List;

public interface MessageService {

    List<MessageResponse> getList(String token);

    MessageResponse getById(String token, long id);

    MessageResponse save(MessageManagerDTO messageManagerDTO, String token);

}
