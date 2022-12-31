package com.example.restapi.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "ambulance")
public class Ambulance extends BaseIdEntity{

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "name")
    private String name;

    @Column(name = "number_plate")
    private String numberPlate;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status")
    private boolean status;

}
