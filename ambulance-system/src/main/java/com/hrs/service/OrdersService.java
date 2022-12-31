package com.hrs.service;

import com.hrs.model.reponse.BookingResponse;
import com.hrs.model.reponse.OrdersResponse;

import java.util.List;

public interface OrdersService {

    List<OrdersResponse> getList(String token);

    List<OrdersResponse> getListProgressPending(String token);
    OrdersResponse getById(String token, long id);
    
}
