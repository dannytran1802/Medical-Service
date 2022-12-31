package com.example.mobileapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.mobileapp.activity.pharmacy.ProductFormActivity;
import com.example.mobileapp.activity.pharmacy.ProductListActivity;
import com.example.mobileapp.activity.user.ShopProductActivity;
import com.example.mobileapp.model.Pharmacy;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.Viewhoder> {

    Context context;
    List<Pharmacy> list;

    public PharmacyAdapter(Context context, List<Pharmacy> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pharma, parent, false);
        return new Viewhoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhoder holder, @SuppressLint("RecyclerView") int position) {
        Pharmacy pharmacy = list.get(position);

        holder.textName.setText(pharmacy.getName());
        holder.textAddress.setText(pharmacy.getAddress());

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContantUtil.pharmacy = pharmacy;

                Intent i = new Intent(context, ShopProductActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("pharmacyId", pharmacy.getId());
                context.startActivity(i);
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

    public class Viewhoder extends RecyclerView.ViewHolder {

        ConstraintLayout mainView;
        TextView textName, textAddress;

        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            mainView = itemView.findViewById(R.id.mainView);
            textName = itemView.findViewById(R.id.textName);
            textAddress = itemView.findViewById(R.id.textAddress);
        }
    }

}