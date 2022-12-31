package com.example.mobileapp.activity.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.HomeUserActivity;
import com.example.mobileapp.activity.LoginActivity;
import com.example.mobileapp.activity.ambulance.AmbulanceActivity;
import com.example.mobileapp.activity.pharmacy.PharmacyActivity;
import com.example.mobileapp.adapter.BookingAdapter;
import com.example.mobileapp.api.BookingAPI;
import com.example.mobileapp.itf.BookingInterface;
import com.example.mobileapp.itf.CheckoutInterface;
import com.example.mobileapp.model.Booking;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class UserHistoryBookingActivity extends AppCompatActivity implements BookingInterface {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_booking);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        BookingAPI bookingAPI = new BookingAPI(UserHistoryBookingActivity.this);
        bookingAPI.findAllBookingByAccount(Long.parseLong(ContantUtil.authDTO.getAccountId()));
    }

    @Override
    public void onBookingPending() {

    }

    @Override
    public void onListBooking(List<Booking> bookingList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductList = findViewById(R.id.recyclerViewSearch);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);

        adapter = new BookingAdapter(getApplicationContext(), getLayoutInflater(), bookingList, false);
        recyclerViewProductList.setAdapter(adapter);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(UserHistoryBookingActivity.this);

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