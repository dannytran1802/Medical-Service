package com.example.mobileapp.activity.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.HomeUserActivity;
import com.example.mobileapp.activity.LoginActivity;
import com.example.mobileapp.activity.ambulance.AmbulanceActivity;
import com.example.mobileapp.activity.pharmacy.PharmacyActivity;
import com.example.mobileapp.activity.pharmacy.PharmacyHistoryActivity;
import com.example.mobileapp.adapter.ShopOrderDetailAdapter;
import com.example.mobileapp.api.CheckoutAPI;
import com.example.mobileapp.dto.OrdersDTO;
import com.example.mobileapp.itf.CheckoutInterface;
import com.example.mobileapp.model.OrderDetails;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class UserHistoryPharmacyDetailActivity extends AppCompatActivity implements CheckoutInterface {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;

    TextView textTotal, textName;
    TextView btnComplete, btnCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_pharmacy_detail);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductList = findViewById(R.id.recyclerView);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);

        List<OrderDetails> ordersList = ContantUtil.orders.getOrderDetails();

        adapter = new ShopOrderDetailAdapter(getApplicationContext(), ordersList, false);
        recyclerViewProductList.setAdapter(adapter);

        Orders orders = ContantUtil.orders;

        textTotal = findViewById(R.id.textTotal);
        textTotal.setText(String.valueOf(orders.getTotalCost()));

        String name = orders.getPharmacyDTO().getName() + " - " + orders.getCreatedOn();
        textName= findViewById(R.id.textName);
        textName.setText(name);

        btnComplete = findViewById(R.id.btnComplete);
        btnCancle = findViewById(R.id.btnCancle);

        String getPharmacyId = ContantUtil.authDTO.getPharmacyId();
        if (getPharmacyId == null) {
            getPharmacyId = "";
        }
        if (getPharmacyId.equalsIgnoreCase("") || Long.parseLong(getPharmacyId) <= 0) {
            btnComplete.setVisibility(View.INVISIBLE);
            btnCancle.setVisibility(View.INVISIBLE);
        }

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show message
                AlertDialog.Builder builder = new AlertDialog.Builder(UserHistoryPharmacyDetailActivity.this);

                // Setting message manually and performing action on button click
                builder.setMessage("Do you want to save your change?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // order
                                OrdersDTO ordersDTO = new OrdersDTO();
                                ordersDTO.setId(ContantUtil.orders.getId());
                                ordersDTO.setProgress("COMPLETED");

                                CheckoutAPI checkoutAPI = new CheckoutAPI(UserHistoryPharmacyDetailActivity.this);
                                checkoutAPI.updateOrderProgress(ordersDTO);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // Creating dialog box
                AlertDialog alert = builder.create();
                // Setting the title manually
                alert.setTitle("Medical Service");
                alert.show();
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show message
                AlertDialog.Builder builder = new AlertDialog.Builder(UserHistoryPharmacyDetailActivity.this);

                // Setting message manually and performing action on button click
                builder.setMessage("Do you want to save your change?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // order
                                OrdersDTO ordersDTO = new OrdersDTO();
                                ordersDTO.setId(ContantUtil.orders.getId());
                                ordersDTO.setProgress("CANCLED");

                                CheckoutAPI checkoutAPI = new CheckoutAPI(UserHistoryPharmacyDetailActivity.this);
                                checkoutAPI.updateOrderProgress(ordersDTO);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // Creating dialog box
                AlertDialog alert = builder.create();
                // Setting the title manually
                alert.setTitle("Medical Service");
                alert.show();
            }
        });
    }


    @Override
    public void onSuccessOrder() {
        Intent intent = new Intent(getApplicationContext(), PharmacyHistoryActivity.class);
        startActivity(intent);
        finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        if (item.getItemId() == R.id.profile) {
            Intent intent = null;
            switch (ContantUtil.roleName) {
                case "USER":
                    intent = new Intent(getApplicationContext(), UserActivity.class);
                    break;
                case "PHARMACY":
                    intent = new Intent(getApplicationContext(), PharmacyActivity.class);
                    break;
                case "AMBULANCE":
                    intent = new Intent(getApplicationContext(), AmbulanceActivity.class);
                    break;
                default:
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    break;
            }
            startActivity(intent);
        }

        if (item.getItemId() == R.id.logout) {
            // show message
            AlertDialog.Builder builder = new AlertDialog.Builder(UserHistoryPharmacyDetailActivity.this);

            // Setting message manually and performing action on button click
            builder.setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish(); // close this activity and return to preview activity (if there is any)
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            // Creating dialog box
            AlertDialog alert = builder.create();
            // Setting the title manually
            alert.setTitle("Medical Service");
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

}