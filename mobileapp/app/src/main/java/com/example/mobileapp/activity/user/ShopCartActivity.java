package com.example.mobileapp.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.adapter.ShopCartAdapter;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ShopCartActivity extends AppCompatActivity {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);

        Intent intent = getIntent();
        long pharmacyId = intent.getLongExtra("pharmacyId", 0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductList = findViewById(R.id.recyclerViewSearch);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);

        List<Product> productList = ContantUtil.getCart();
        adapter = new ShopCartAdapter(getApplicationContext(), productList, true);
        recyclerViewProductList.setAdapter(adapter);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.btnSubmit);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UserCheckoutActivity.class);
                startActivity(i);
            }
        });
    }

}