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
import com.example.mobileapp.dto.MessageDTO;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewhoder> {

    Activity activity;
    List<MessageDTO> list;

    public MessageAdapter(Activity activity, List<MessageDTO> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageViewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_message_item, parent, false);
        return new MessageViewhoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewhoder holder, @SuppressLint("RecyclerView") int position) {
        MessageDTO messageDTO = list.get(position);

        if (messageDTO.getTime().contains("BOOKING")) {
            holder.textTitle.setText("Booking - " + messageDTO.getTime().toString());
        } else {
            holder.textTitle.setText(messageDTO.getTitle() + " - " + messageDTO.getTime().toString());
        }

        holder.textBody.setText(messageDTO.getContent());
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }

        return list.size();
    }

    public class MessageViewhoder extends RecyclerView.ViewHolder {
        TextView textTitle, textBody;

        public MessageViewhoder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textBody = itemView.findViewById(R.id.textBody);
        }
    }

}