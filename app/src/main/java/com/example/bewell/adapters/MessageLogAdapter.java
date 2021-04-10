package com.example.bewell.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bewell.R;
import com.example.bewell.models.User;

import java.util.ArrayList;

public class MessageLogAdapter extends RecyclerView.Adapter<MessageLogAdapter.ViewHolder> {

    private ArrayList<User> userMessageLogs = new ArrayList<>();


    public MessageLogAdapter() {
    }


    @NonNull
    @Override
    public MessageLogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message_from, parent, false);
        MessageLogAdapter.ViewHolder holder = new MessageLogAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageLogAdapter.ViewHolder holder, int position) {
        User userRowAt  = userMessageLogs.get(position);
        holder.messageFrom.setText(userRowAt.getEmpId());

    }

    @Override
    public int getItemCount() {
        return userMessageLogs.size();

    }

    public void setUserMessageLogs(ArrayList<User> userMessageLogs) {
        this.userMessageLogs = userMessageLogs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView messageFrom;
        private TextView messageTo;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageFrom  =  itemView.findViewById(R.id.usernameMessageFrom);


        }


    }



}
