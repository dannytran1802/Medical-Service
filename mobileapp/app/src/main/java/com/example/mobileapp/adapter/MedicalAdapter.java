package com.example.mobileapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.model.Medical;

import java.util.List;

public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MessageViewhoder> {

    Activity activity;
    List<Medical> list;

    public MedicalAdapter(Activity activity, List<Medical> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageViewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_medical, parent, false);
        return new MessageViewhoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewhoder holder, @SuppressLint("RecyclerView") int position) {
        Medical medical = list.get(position);

        holder.textTitle.setText(medical.getName());
        holder.textBody.setText("- Address: " + medical.getAddress());
        holder.textContact.setText("- Phone: " + medical.getContact());
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }

        return list.size();
    }

    public class MessageViewhoder extends RecyclerView.ViewHolder {

        TextView textTitle, textBody, textContact;

        public MessageViewhoder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textBody = itemView.findViewById(R.id.textBody);
            textContact = itemView.findViewById(R.id.textContact);
        }
    }

}