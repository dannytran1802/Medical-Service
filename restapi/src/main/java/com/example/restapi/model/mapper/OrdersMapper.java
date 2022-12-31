package com.example.restapi.model.mapper;

import com.example.restapi.model.dto.OrdersDTO;
import com.example.restapi.model.entity.Orders;

import java.util.List;

public interface OrdersMapper {

    // Map Entity to DTO
    OrdersDTO toDTO(Orders orders);

    List<OrdersDTO> toListDTO(List<Orders> ordersList);

    // Map DTO to Entity
    Orders toEntity(OrdersDTO ordersDTO);
    
}
