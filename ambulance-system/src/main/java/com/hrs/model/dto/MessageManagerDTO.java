package com.hrs.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageManagerDTO {
    private long id;
    private String title;
    private String content;
    private boolean status;

}
