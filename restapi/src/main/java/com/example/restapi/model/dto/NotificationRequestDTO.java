package com.example.restapi.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotificationRequestDTO {

    private List<String> targetMultiDevice;
    private String target;
    private String title;
    private String body;

}
