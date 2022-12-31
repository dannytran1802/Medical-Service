package com.example.mobileapp.itf;

import com.example.mobileapp.dto.MessageDTO;
import com.example.mobileapp.model.Product;

import java.util.List;

public interface MessageInterface {

    void onSuccess(List<MessageDTO> messageList);

    void onError(List<String> errors);

}
