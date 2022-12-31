package com.example.restapi.service;

import com.example.restapi.model.dto.OrdersDTO;
import com.example.restapi.model.entity.Orders;

import java.util.List;

public interface OrdersService {

    List<Orders> findAll();

    List<Orders> findAllByProgress(String progress);

    Orders findById(long id);

    List<Orders> findByAccountId(long accountId);

    List<Orders> findByPharmacyId(long pharmacyId);

    Orders save(Orders orders);

    Orders save(OrdersDTO ordersDTO);

}
