package com.example.restapi.controller;

import com.example.restapi.model.dto.OrdersDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.OrderDetails;
import com.example.restapi.model.entity.Orders;
import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.mapper.OrderDetailsMapper;
import com.example.restapi.model.mapper.OrdersMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.OrderDetailsService;
import com.example.restapi.service.OrdersService;
import com.example.restapi.service.PharmacyService;
import com.example.restapi.utils.FormatUtil;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.OrdersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    @Autowired
    private OrdersValidator ordersValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping("/list")
    public ResponseEntity<RestReponseDTO> list() {
        RestReponseDTO restResponse = new RestReponseDTO();
        List<Orders> ordersList = ordersService.findAll();

        if (ordersList == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }
        restResponse.ok(ordersMapper.toListDTO(ordersList));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/find-progress")
    public ResponseEntity<RestReponseDTO> getListProgressPending() {
        RestReponseDTO restResponse = new RestReponseDTO();
        List<Orders> ordersList = ordersService.findAllByProgress("PENDING");

        if (ordersList == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }
        restResponse.ok(ordersMapper.toListDTO(ordersList));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> findById(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();

        Orders orders = ordersService.findById(id);

        if (orders == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        restResponse.ok(ordersMapper.toDTO(orders));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<RestReponseDTO> findByAccountId(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();

        Account account = accountService.findById(id);

        if (account == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        List<Orders> orderList = ordersService.findByAccountId(account.getId());

        List<OrdersDTO> ordersDTOList = ordersMapper.toListDTO(orderList);
        if (ordersDTOList != null) {
            for (OrdersDTO ordersDTO : ordersDTOList) {
                List<OrderDetails> orderDetailsList = orderDetailsService.findByOrders(ordersDTO.getId());
                ordersDTO.setOrderDetails(orderDetailsMapper.toListDTO(orderDetailsList));
            }
        }

        restResponse.ok(ordersDTOList);
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/pharmacy/{id}")
    public ResponseEntity<RestReponseDTO> findByPharmacyId(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();

        Pharmacy pharmacy = pharmacyService.findById(id);

        if (pharmacy == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        List<Orders> orderList = ordersService.findByPharmacyId(pharmacy.getId());

        List<OrdersDTO> ordersDTOList = ordersMapper.toListDTO(orderList);
        if (ordersDTOList != null) {
            for (OrdersDTO ordersDTO : ordersDTOList) {
                List<OrderDetails> orderDetailsList = orderDetailsService.findByOrders(ordersDTO.getId());
                ordersDTO.setOrderDetails(orderDetailsMapper.toListDTO(orderDetailsList));
            }
        }

        restResponse.ok(ordersDTOList);
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<RestReponseDTO> save(@RequestBody OrdersDTO ordersDTO, BindingResult bindingResult){
        RestReponseDTO restResponse = new RestReponseDTO();

        // validator
        ordersValidator.validate(ordersDTO, bindingResult);

        if (bindingResult.hasErrors() && ordersDTO.getAccountDTO().getRoleId() != 2){
            restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        Orders orders = ordersService.findById(ordersDTO.getId());

        if (orders == null) {
            orders = new Orders();
        }

        Pharmacy pharmacy = pharmacyService.findById(ordersDTO.getPharmacyId());
        orders.setAddress(ordersDTO.getAddress());
        orders.setTotalCost(FormatUtil.formatNumber(ordersDTO.getTotalCost()));
        orders.setAccount(accountService.findById(ordersDTO.getAccountId()));
        orders.setPharmacy(pharmacy);
        orders.setStatus(true);
        orders.setProgress("review");

        ordersService.save(orders);
        restResponse.ok(ordersMapper.toDTO(orders));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/progress")
    public ResponseEntity<RestReponseDTO> updateProgress(@RequestBody OrdersDTO ordersDTO, BindingResult bindingResult){
        RestReponseDTO restResponse = new RestReponseDTO();

        Orders orders = ordersService.findById(ordersDTO.getId());
        if (orders != null) {
            orders.setProgress(ordersDTO.getProgress());
            ordersService.save(orders);
            restResponse.ok(ordersMapper.toDTO(orders));
        } else {
            restResponse.fail();
        }

        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RestReponseDTO> delete(@PathVariable long id){
        RestReponseDTO restResponse = new RestReponseDTO();

        Orders orders = ordersService.findById(id);

        if(orders == null){
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        // set status order_detail
        List<OrderDetails> orderDetailList = orderDetailsService.findByOrders(orders);
        for (OrderDetails orderDetails : orderDetailList){
            orderDetails.setStatus(false);
        }

        orders.setStatus(false);
        orders.setProgress("canceled");

        ordersService.save(orders);
        restResponse.ok(ordersMapper.toDTO(orders));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

}
