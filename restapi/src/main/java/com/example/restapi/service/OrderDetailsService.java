package com.example.restapi.service;

import com.example.restapi.model.entity.OrderDetails;
import com.example.restapi.model.entity.Orders;

import java.util.List;

public interface OrderDetailsService {

    List<OrderDetails> findAll();

    OrderDetails findById(long id);

    List<OrderDetails> findByOrders(Orders orders);

    List<OrderDetails> findByOrders(long ordersId);

    OrderDetails save(OrderDetails orderDetails);
}
