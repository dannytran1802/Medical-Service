package com.example.restapi.model.mapper.impl;

import com.example.restapi.model.dto.OrderDetailsDTO;
import com.example.restapi.model.dto.OrdersDTO;
import com.example.restapi.model.dto.ProductDTO;
import com.example.restapi.model.entity.OrderDetails;
import com.example.restapi.model.entity.Orders;
import com.example.restapi.model.entity.Product;
import com.example.restapi.model.mapper.OrderDetailsMapper;
import com.example.restapi.model.mapper.OrdersMapper;
import com.example.restapi.model.mapper.ProductMapper;
import com.example.restapi.service.OrderDetailsService;
import com.example.restapi.service.OrdersService;
import com.example.restapi.service.ProductService;
import com.example.restapi.utils.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDetailsMapperImpl implements OrderDetailsMapper {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public OrderDetailsDTO toDTO(OrderDetails orderDetails) {

        if (orderDetails == null){
            return null;
        }

        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        orderDetailsDTO.setId(orderDetails.getId());
        orderDetailsDTO.setQuantity(String.valueOf(orderDetails.getQuantity()));
        orderDetailsDTO.setStatus(orderDetails.isStatus());
        orderDetailsDTO.setPrice(FormatUtil.formatNumber(orderDetails.getPrice()));

        Orders orders = ordersService.findById(orderDetails.getOrder().getId());
        OrdersDTO ordersDTO = new OrdersDTO();
        if (orders != null){
            ordersDTO = ordersMapper.toDTO(orders);
        }
        orderDetailsDTO.setOrderDTO(ordersDTO);

        Product product = productService.findById(orderDetails.getProduct().getId());
        ProductDTO productDTO = new ProductDTO();
        if (product != null) {
            productDTO = productMapper.toDTO(product);
        }
        orderDetailsDTO.setProductDTO(productDTO);

        return orderDetailsDTO;
    }

    @Override
    public List<OrderDetailsDTO> toListDTO(List<OrderDetails> orderDetailList) {
        if (orderDetailList == null) {
            return null;
        }
        List<OrderDetailsDTO> list = new ArrayList<>(orderDetailList.size());
        for (OrderDetails orderDetails : orderDetailList) {
            OrderDetailsDTO orderDetailsDTO = toDTO(orderDetails);
            if (orderDetailsDTO != null) {
                list.add(orderDetailsDTO);
            }
        }

        return list;
    }

    @Override
    public OrderDetails toEntity(OrderDetailsDTO orderDetailsDTO) {

        if (orderDetailsDTO == null){
            return null;
        }

        OrderDetails orderDetails = orderDetailsService.findById(orderDetailsDTO.getId());

        if (orderDetails == null){
            orderDetails = new OrderDetails();
        }

        orderDetails.setOrder(ordersService.findById(orderDetailsDTO.getOrderId()));
        orderDetails.setProduct(productService.findById(orderDetailsDTO.getProductId()));
        orderDetails.setQuantity(Integer.parseInt(orderDetailsDTO.getQuantity()));
        orderDetails.setPrice(FormatUtil.formatNumber(orderDetailsDTO.getPrice()));
        orderDetails.setStatus(orderDetailsDTO.isStatus());

        return orderDetails;
    }
}
