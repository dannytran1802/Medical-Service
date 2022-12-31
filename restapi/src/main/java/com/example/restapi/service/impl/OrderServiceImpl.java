package com.example.restapi.service.impl;

import com.example.restapi.model.dto.OrderDetailsDTO;
import com.example.restapi.model.dto.OrdersDTO;
import com.example.restapi.model.entity.*;
import com.example.restapi.repository.OrderDetailsRepository;
import com.example.restapi.repository.OrdersRepository;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.OrdersService;
import com.example.restapi.service.PharmacyService;
import com.example.restapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private ProductService productService;

    @Override
    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    @Override
    public List<Orders> findAllByProgress(String progress) {
        return ordersRepository.findByProgress(progress);
    }

    @Override
    public Orders findById(long id) {
        return ordersRepository.findById(id).orElse(null);
    }

    @Override
    public List<Orders> findByAccountId(long accountId) {
        return ordersRepository.findByAccountId(accountId);
    }

    @Override
    public List<Orders> findByPharmacyId(long pharmacyId) {
        return ordersRepository.findByPharmacyId(pharmacyId);
    }

    @Override
    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Override
    public Orders save(OrdersDTO ordersDTO) {
        Orders orders = new Orders();

        // set order
        Account account = accountService.findById(ordersDTO.getAccountId());
        orders.setAccount(account);

        Pharmacy pharmacy = pharmacyService.findById(ordersDTO.getPharmacyId());
        orders.setPharmacy(pharmacy);

        orders.setAddress(account.getAddress());
        orders.setProgress("PENDING");
        orders.setTotalCost(Double.parseDouble(ordersDTO.getTotalCost()));
        orders.setStatus(true);

        ordersRepository.save(orders);

        // set order detail
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        List<OrderDetailsDTO> orderDetailsDTOList = ordersDTO.getOrderDetails();
        if (orderDetailsDTOList != null) {
            for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOList) {
                Product product = productService.findById(orderDetailsDTO.getProductId());

                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrder(orders);
                orderDetails.setProduct(product);
                orderDetails.setPrice(Double.parseDouble(orderDetailsDTO.getPrice()));
                orderDetails.setQuantity(Integer.parseInt(orderDetailsDTO.getQuantity()));
                orderDetails.setStatus(true);

                orderDetailsList.add(orderDetails);
            }
            orderDetailsRepository.saveAll(orderDetailsList);
        }

        return orders;
    }

}
