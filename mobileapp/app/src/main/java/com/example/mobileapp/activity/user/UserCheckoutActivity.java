package com.example.mobileapp.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.HomeUserActivity;
import com.example.mobileapp.adapter.ShopCartAdapter;
import com.example.mobileapp.api.CheckoutAPI;
import com.example.mobileapp.dto.OrderDetailsDTO;
import com.example.mobileapp.dto.OrdersDTO;
import com.example.mobileapp.itf.CheckoutInterface;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;

public class UserCheckoutActivity extends AppCompatActivity implements CheckoutInterface {

    RecyclerView.Adapter adapter;

    RecyclerView recyclerViewList;
    TextView totalFeeTxt, taxTxt, deliveryTxt, textTotal, emptyTxt, btnCheckout, momo;
    double tax;
    ScrollView scrollView;

    double total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_checkout);

        initView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        List<Product> productList = ContantUtil.getCartActive();
        adapter = new ShopCartAdapter(getApplicationContext(), productList, false);
        recyclerViewList.setAdapter(adapter);

        // set total
        if (productList != null) {
            for (Product product : productList) {
                total += Double.parseDouble(product.getPrice().replace(",", "")) * product.getQty();
            }
        }
        textTotal.setText(String.valueOf(total));

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // order
                OrdersDTO ordersDTO = new OrdersDTO();
                ordersDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                ordersDTO.setPharmacyId(ContantUtil.pharmacyId);
                ordersDTO.setTotalCost(String.valueOf(total));

                // order detail
                List<OrderDetailsDTO> ordersDTOS = new ArrayList<>();
                if (productList != null) {
                    for (Product product : productList) {
                        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
                        orderDetailsDTO.setProductId(product.getId());
                        orderDetailsDTO.setPrice(Double.parseDouble(product.getPrice().replace(",", "")));
                        orderDetailsDTO.setQuantity(product.getQty());
                        ordersDTOS.add(orderDetailsDTO);
                    }
                }
                ordersDTO.setOrderDetails(ordersDTOS);

                CheckoutAPI checkoutAPI = new CheckoutAPI(UserCheckoutActivity.this);
                checkoutAPI.saveOrder(ordersDTO);
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerView);
        textTotal = findViewById(R.id.textTotal);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView4);
        btnCheckout = findViewById(R.id.btnCheckout);
        momo = findViewById(R.id.checkoutmomo);
    }


    @Override
    public void onSuccessOrder() {
        finish();
        Intent intent = new Intent(getApplicationContext(), UserHistoryPharmacyActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccessBooking() {

    }

    @Override
    public void onFetchOrders(List<Orders> ordersList) {

    }

    @Override
    public void onFetchBookings() {

    }

    @Override
    public void onError(List<String> errors) {

    }

}