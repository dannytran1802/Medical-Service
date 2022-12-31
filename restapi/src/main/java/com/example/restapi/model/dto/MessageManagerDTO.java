package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageManagerDTO {

    private long id;
    private String title;
    private String content;
    private boolean status;

}
