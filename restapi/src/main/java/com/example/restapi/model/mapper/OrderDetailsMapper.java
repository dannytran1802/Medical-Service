package com.example.restapi.model.mapper;

import com.example.restapi.model.dto.OrderDetailsDTO;
import com.example.restapi.model.entity.OrderDetails;

import java.util.List;


public interface OrderDetailsMapper {
    // Map Entity to DTO
    OrderDetailsDTO toDTO(OrderDetails orderDetails);

    List<OrderDetailsDTO> toListDTO(List<OrderDetails> orderDetailList);

    // Map DTO to Entity
    OrderDetails toEntity(OrderDetailsDTO orderDetailsDTO);
}
