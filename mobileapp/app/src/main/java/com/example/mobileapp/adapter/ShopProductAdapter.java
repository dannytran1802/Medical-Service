package com.example.mobileapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.HomeAmbulanceActivity;
import com.example.mobileapp.activity.pharmacy.ProductFormActivity;
import com.example.mobileapp.activity.pharmacy.ProductListActivity;
import com.example.mobileapp.activity.user.ShopProductActivity;
import com.example.mobileapp.api.BookingAPI;
import com.example.mobileapp.dto.BookingDTO;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;

public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ProductViewhoder> {

    Activity activity;
    Context context;
    List<Product> list;

    public ShopProductAdapter(Activity activity, Context context, List<Product> list) {
        this.activity = activity;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductViewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_shop_product, parent, false);
        return new ProductViewhoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewhoder holder, @SuppressLint("RecyclerView") int position) {
        Product product = list.get(position);

        holder.textName.setText(product.getName());
        holder.textPrice.setText(product.getPrice());

        if (!product.isOtc()) {
            holder.textName.setTextColor(Color.parseColor("#FF000000"));
        } else {
            holder.textName.setTextColor(Color.parseColor("#FF0000"));
        }

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.isOtc()) {
                    // show message
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    // Setting message manually and performing action on button click
                    builder.setMessage("This is a prescription drug, please contact the pharmacy!")
                            .setCancelable(false)
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    // Creating dialog box
                    AlertDialog alert = builder.create();
                    // Setting the title manually
                    alert.setTitle("Message");
                    alert.show();
                } else {
                    product.setAddToCart(!product.isAddToCart());
                    if (product.isAddToCart()) {
                        holder.btnAdd.setText("Remove");
                        holder.btnAdd.setTextColor(Color.RED);
                        ContantUtil.addToCart(product.getId(), product);
                    } else {
                        holder.btnAdd.setText("Add to cart");
                        holder.btnAdd.setTextColor(Color.BLUE);
                        ContantUtil.deleteToCart(product.getId());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ProductViewhoder extends RecyclerView.ViewHolder {

        ConstraintLayout main;
        TextView textName, textPrice, btnAdd;

        public ProductViewhoder(@NonNull View itemView) {
            super(itemView);
            main = itemView.findViewById(R.id.mainPopular);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }

}