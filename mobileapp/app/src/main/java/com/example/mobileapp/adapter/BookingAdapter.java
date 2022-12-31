package com.example.mobileapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.model.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ProductViewhoder> {

    Context context;
    List<Booking> list;
    boolean isUpdate;

    private LayoutInflater layoutInflater = null;
    private AlertDialog alertDialog = null;

    public BookingAdapter(Context context, LayoutInflater layoutInflater, List<Booking> list, boolean isUpdate) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.list = list;
        this.isUpdate = isUpdate;
    }

    @NonNull
    @Override
    public ProductViewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_booking, parent, false);
        return new ProductViewhoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewhoder holder, @SuppressLint("RecyclerView") int position) {
        Booking booking = list.get(position);

        if (isUpdate) {
            holder.textName.setText(booking.getAccountDTO().getFullName());
            holder.textQty.setText("");

            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    showAlertDialog(view, booking);
                }
            });

        } else {
            holder.textName.setText(booking.getAmbulanceDTO().getAccountDTO().getFullName());
            holder.textQty.setText(String.valueOf(booking.getCreatedOn()));
        }
        holder.textDate.setText(String.valueOf(booking.getCreatedOn()));
        holder.textTotal.setText(String.valueOf(booking.getProgress()));
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

    public void showAlertDialog(View view, Booking booking) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(booking.getCreatedOn());

        final View customLayout = layoutInflater.inflate(R.layout.dialog_booking, null);
        builder.setView(customLayout);

        builder.setPositiveButton(
                "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }


}