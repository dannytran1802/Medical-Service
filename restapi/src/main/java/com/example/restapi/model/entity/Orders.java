package com.example.restapi.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    // review || payment || completed || canceled || shipping
    @Column(name = "progress")
    private String progress;

    @Column(name = "address")
    private String address;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "status")
    private boolean status;

}
