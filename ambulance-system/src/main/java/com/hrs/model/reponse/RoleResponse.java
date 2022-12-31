package com.hrs.model.reponse;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RoleResponse {
    public int version;
    public String createdOn;
    public String updatedOn;
    public int id;
    public String name;
    public String description;
    public boolean status;

}
