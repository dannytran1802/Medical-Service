package com.example.mobileapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.model.OrderDetails;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class ShopOrderDetailAdapter extends RecyclerView.Adapter<ShopOrderDetailAdapter.ProductViewhoder> {

    Context context;
    List<OrderDetails> list;
    boolean isUpdate;

    public ShopOrderDetailAdapter(Context context, List<OrderDetails> list, boolean isUpdate) {
        this.context = context;
        this.list = list;
        this.isUpdate = isUpdate;
    }

    @NonNull
    @Override
    public ProductViewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_shop_order_detail, parent, false);
        return new ProductViewhoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewhoder holder, @SuppressLint("RecyclerView") int position) {
        OrderDetails orders = list.get(position);

        double total = Double.parseDouble(orders.getQuantity()) * Double.parseDouble(orders.getPrice().replace(",", ""));

        holder.textName.setText(orders.getProductDTO().getName());
        holder.textQty.setText(String.valueOf(orders.getQuantity()));
        holder.textDate.setText(String.valueOf(orders.getPrice()));
        holder.textTotal.setText(String.valueOf(total));
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
        TextView textName, textDate, textQty, textTotal;
        ImageView btnRemove, btnAdd;

        public ProductViewhoder(@NonNull View itemView) {
            super(itemView);
            main = itemView.findViewById(R.id.mainPopular);
            textName = itemView.findViewById(R.id.textName);
            textDate = itemView.findViewById(R.id.textDate);
            textQty = itemView.findViewById(R.id.textQty);
            textTotal = itemView.findViewById(R.id.textTotal);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}