package com.hrs.service;

import com.hrs.model.reponse.OrderDetailsResponse;

import java.util.List;

public interface OrderDetailsService {

    List<OrderDetailsResponse> getListByOrder(String token, long ordersId);
}
