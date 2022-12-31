package com.example.restapi.service.impl;

import com.example.restapi.model.entity.OrderDetails;
import com.example.restapi.model.entity.Orders;
import com.example.restapi.repository.OrderDetailsRepository;
import com.example.restapi.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<OrderDetails> findAll() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public OrderDetails findById(long id) {
        return orderDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderDetails> findByOrders(Orders orders) {
        return orderDetailsRepository.findByOrderId(orders.getId());
    }

    @Override
    public List<OrderDetails> findByOrders(long ordersId) {
        return orderDetailsRepository.findByOrderId(ordersId);
    }

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }
}
