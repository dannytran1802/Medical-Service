package com.example.restapi.model.mapper.impl;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.OrdersDTO;
import com.example.restapi.model.dto.PharmacyDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Orders;
import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.mapper.OrdersMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.OrdersService;
import com.example.restapi.service.PharmacyService;
import com.example.restapi.utils.DateUtil;
import com.example.restapi.utils.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class OrdersMapperImpl implements OrdersMapper {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private OrdersService ordersService;

    @Override
    public OrdersDTO toDTO(Orders orders) {

        if (orders == null){
            return null;
        }
        
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setId(orders.getId());
        ordersDTO.setProgress(orders.getProgress());
        ordersDTO.setAddress(orders.getAddress());
        ordersDTO.setTotalCost(FormatUtil.formatNumber(orders.getTotalCost()));
        ordersDTO.setStatus(orders.isStatus());

        Account account = accountService.findById(orders.getAccount().getId());
        AccountDTO accountDTO = new AccountDTO();
        if(account != null){
            accountDTO.setId(account.getId());
            accountDTO.setFullName(account.getFullName());
            accountDTO.setPhone(account.getPhone());
            accountDTO.setEmail(account.getEmail());
        }
        ordersDTO.setAccountDTO(accountDTO);
        ordersDTO.setAccountId(accountDTO.getId());

        Pharmacy pharmacy = pharmacyService.findById(orders.getPharmacy().getId());
        PharmacyDTO pharmacyDTO = new PharmacyDTO();
        if(pharmacy != null){
            pharmacyDTO.setId(pharmacy.getId());
            pharmacyDTO.setName(pharmacy.getName());
            pharmacyDTO.setAddress(pharmacy.getAddress());

            pharmacyDTO.setAccountDTO(accountDTO);
            pharmacyDTO.setAccountId(pharmacy.getAccount().getId());
        }
        ordersDTO.setPharmacyDTO(pharmacyDTO);
        ordersDTO.setPharmacyId(pharmacyDTO.getId());

        ordersDTO.setCreatedOn(DateUtil.convertDateToString(orders.getCreatedOn(), "dd-MM-yyyy"));

        return ordersDTO;
    }

    @Override
    public List<OrdersDTO> toListDTO(List<Orders> ordersList) {
        if (ordersList == null) {
            return null;
        }
        List<OrdersDTO> list = new ArrayList<>(ordersList.size());
        for (Orders orders : ordersList) {
            OrdersDTO ordersDTO = toDTO(orders);
            if (ordersDTO != null) {
                list.add(ordersDTO);
            }
        }

        return list;
    }

    @Override
    public Orders toEntity(OrdersDTO ordersDTO) {
        Orders orders = ordersService.findById(ordersDTO.getId());

        if (orders == null){
            orders = new Orders();
        }

        orders.setAddress(ordersDTO.getAddress());
        orders.setProgress(ordersDTO.getProgress());
        orders.setStatus(ordersDTO.isStatus());
        orders.setTotalCost(FormatUtil.formatNumber(ordersDTO.getTotalCost()));
        orders.setAccount(accountService.findById(ordersDTO.getAccountId()));
        orders.setPharmacy(pharmacyService.findById(ordersDTO.getPharmacyId()));

        return orders;
    }
}
