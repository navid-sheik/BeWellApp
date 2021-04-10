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

public class ConversationRecViewAdapter extends  RecyclerView.Adapter<ConversationRecViewAdapter.ViewHolder>{

    private ArrayList<User> conversations = new ArrayList<>();
    private ConversationRecViewAdapter.OnConversationLister onConversationLister;

    public ConversationRecViewAdapter() {
    }

    public ConversationRecViewAdapter(ConversationRecViewAdapter.OnConversationLister onConversationLister) {
        this.onConversationLister = onConversationLister;
    }

    public ArrayList<User> getConversations() {
        return conversations;
    }

    public void setAmbassadorContacts(ArrayList<User> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConversationRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_individual_conversation, parent, false);
        ConversationRecViewAdapter.ViewHolder holder = new ConversationRecViewAdapter.ViewHolder(view, onConversationLister);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationRecViewAdapter.ViewHolder holder, int position) {
        User userRowAt  = conversations.get(position);
        holder.convUsername.setText(userRowAt.getEmpId());
        holder.latestMessageCov.setText(userRowAt.getName());

    }

    @Override
    public int getItemCount() {
        return conversations.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView convUsername;
        private TextView  latestMessageCov;

        ConversationRecViewAdapter.OnConversationLister onConversationLister;

        public ViewHolder(@NonNull View itemView, ConversationRecViewAdapter.OnConversationLister onConversationLister) {
            super(itemView);
            convUsername = itemView.findViewById(R.id.usernameConversation);
            latestMessageCov = itemView.findViewById(R.id.userNameLatestMessage);


            this.onConversationLister = onConversationLister;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.v("Something", "It clicked");
            onConversationLister.onConversationClick(getAdapterPosition());

        }
    }

    public interface OnConversationLister {
        void onConversationClick(int position);
    }
}
