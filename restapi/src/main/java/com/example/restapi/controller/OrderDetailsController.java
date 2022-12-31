package com.example.restapi.controller;

import com.example.restapi.model.dto.OrderDetailsDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.OrderDetails;
import com.example.restapi.model.entity.Orders;
import com.example.restapi.model.mapper.OrderDetailsMapper;
import com.example.restapi.service.OrderDetailsService;
import com.example.restapi.service.OrdersService;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.OrderDetailsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderDetails")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    @Autowired
    private OrderDetailsValidator orderDetailsValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping("/list")
    public ResponseEntity<RestReponseDTO> list(){
        RestReponseDTO restResponse = new RestReponseDTO();

        List<OrderDetails> orderDetailList = orderDetailsService.findAll();

        if (orderDetailList == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        restResponse.ok(orderDetailsMapper.toListDTO(orderDetailList));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> findById(@PathVariable long id){
        RestReponseDTO restResponse = new RestReponseDTO();

        OrderDetails orderDetails = orderDetailsService.findById(id);

        if (orderDetails == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        restResponse.ok(orderDetailsMapper.toDTO(orderDetails));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<RestReponseDTO> findByOrderId(@PathVariable long id){
        RestReponseDTO restResponse = new RestReponseDTO();

        Orders orders = ordersService.findById(id);

        if (orders == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        List<OrderDetails> orderDetailList = orderDetailsService.findByOrders(orders);
        restResponse.ok(orderDetailsMapper.toListDTO(orderDetailList));

        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<RestReponseDTO> save(@RequestBody OrderDetailsDTO orderDetailsDTO, BindingResult bindingResult){
        RestReponseDTO restResponse = new RestReponseDTO();

        orderDetailsValidator.validate(orderDetailsDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        Orders orders = ordersService.findById(orderDetailsDTO.getOrderId());
        OrderDetails orderDetails = new OrderDetails();

        if (orders != null){
            orderDetails = orderDetailsMapper.toEntity(orderDetailsDTO);
        }
        orderDetailsService.save(orderDetails);
        restResponse.ok(orderDetailsMapper.toDTO(orderDetails));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RestReponseDTO> delete(@PathVariable long id){
        RestReponseDTO restResponse = new RestReponseDTO();

        OrderDetails orderDetails = orderDetailsService.findById(id);

        if (orderDetails == null){
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }
        orderDetails.setStatus(false);
        orderDetailsService.save(orderDetails);
        restResponse.ok(orderDetailsMapper.toDTO(orderDetails));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

}
