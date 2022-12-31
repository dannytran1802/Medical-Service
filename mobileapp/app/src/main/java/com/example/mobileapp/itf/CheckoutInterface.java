package com.example.mobileapp.itf;

import com.example.mobileapp.model.Orders;

import java.util.List;

public interface CheckoutInterface {

    void onSuccessOrder();

    void onSuccessBooking();

    void onFetchOrders(List<Orders> ordersList);

    void onFetchBookings();

    void onError(List<String> errors);

}
