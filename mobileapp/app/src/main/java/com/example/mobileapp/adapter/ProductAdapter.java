package com.example.mobileapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.activity.HomeAmbulanceActivity;
import com.example.mobileapp.activity.pharmacy.ProductFormActivity;
import com.example.mobileapp.activity.pharmacy.ProductListActivity;
import com.example.mobileapp.api.BookingAPI;
import com.example.mobileapp.api.ProductAPI;
import com.example.mobileapp.dto.BookingDTO;
import com.example.mobileapp.model.Product;

import java.util.List;

import com.example.mobileapp.R;
import com.example.mobileapp.util.ContantUtil;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewhoder> {

    Activity activity;
    List<Product> list;

    public ProductAdapter(Activity activity, List<Product> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductViewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product, parent, false);
        return new ProductViewhoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewhoder holder, @SuppressLint("RecyclerView") int position) {
        Product product = list.get(position);

        holder.title.setText(product.getName());
        holder.price.setText(product.getPrice());

        if (!product.isOtc()) {
            holder.title.setTextColor(Color.parseColor("#FF000000"));
        } else {
            holder.title.setTextColor(Color.parseColor("#FF0000"));
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ProductFormActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("productId", product.getId());
                bundle.putLong("pharmacyId", product.getPharmacyId());
                bundle.putString("name", product.getName());
                bundle.putString("price", product.getPrice());
                bundle.putString("description", product.getDescription());
                bundle.putBoolean("otc", product.isOtc());
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show message
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                // Setting message manually and performing action on button click
                builder.setMessage("Confirm you want to delete [" + product.getName().toUpperCase() + "]?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ProductAPI productAPI = new ProductAPI(null);
                                productAPI.deleteProduct(product.getId());

                                ProgressDialog progress = ProgressDialog.show(activity, "Progress",
                                        "Please wait...", true);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.dismiss();

                                        Intent intent = new Intent(holder.itemView.getContext(), ProductListActivity.class);
                                        holder.itemView.getContext().startActivity(intent);
                                    }
                                }, 3000);
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
                alert.setTitle("Message");
                alert.show();
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

        TextView title, price, edit, delete;
        ImageView imagePic;

        public ProductViewhoder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.fee);
            imagePic = itemView.findViewById(R.id.pic);
            edit = itemView.findViewById(R.id.editbtn);
            delete = itemView.findViewById(R.id.deletebtn);
        }
    }

}