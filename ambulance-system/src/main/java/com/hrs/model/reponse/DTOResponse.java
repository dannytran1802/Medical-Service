package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DTOResponse<T> {
    private String status;
    private Messages messages;
    private T data;
    private Metadata metadata;
}
