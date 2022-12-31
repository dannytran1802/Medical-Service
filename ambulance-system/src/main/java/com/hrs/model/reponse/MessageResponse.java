package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {

    public int version;
    public String createdOn;
    public String updatedOn;
    public int id;
    public String title;
    public String content;
    public boolean status;

}
