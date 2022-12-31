package com.example.mobileapp.activity.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.ChatActivity;
import com.example.mobileapp.activity.HomeUserActivity;
import com.example.mobileapp.activity.LoginActivity;
import com.example.mobileapp.activity.VideoCallActivity;
import com.example.mobileapp.activity.ambulance.AmbulanceActivity;
import com.example.mobileapp.activity.pharmacy.PharmacyActivity;
import com.example.mobileapp.adapter.ShopProductAdapter;
import com.example.mobileapp.api.ProductAPI;
import com.example.mobileapp.itf.ProductInterface;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ShopProductActivity extends AppCompatActivity implements ProductInterface {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_product_list);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        long pharmacyId = intent.getLongExtra("pharmacyId", 0);
        ContantUtil.pharmacyId = pharmacyId;

        ProductAPI productAPI = new ProductAPI(ShopProductActivity.this);
        productAPI.findAllProductByPharmacy(pharmacyId);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.btnViewCart);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShopCartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("pharmacyId", pharmacyId);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        FloatingActionButton myFabVideo = (FloatingActionButton) findViewById(R.id.btnVideo);
        myFabVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VideoCallActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton myFabChat = (FloatingActionButton) findViewById(R.id.btnChat);
        myFabChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onSuccess(List<Product> productList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL,false);
        recyclerViewProductList = findViewById(R.id.recyclerViewSearch);
        recyclerViewProductList.setLayoutManager(gridLayoutManager);

        adapter = new ShopProductAdapter(ShopProductActivity.this, getApplicationContext(), productList);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ShopProductActivity.this);

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