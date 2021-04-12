package com.example.bewell.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bewell.R;
import com.example.bewell.models.Message;
import com.example.bewell.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversationRecViewAdapter extends  RecyclerView.Adapter<ConversationRecViewAdapter.ViewHolder>{

    private ArrayList<Message> latestMessages = new ArrayList<>();
    private ConversationRecViewAdapter.OnConversationLister onConversationLister;
    private User otherUser;

    public ConversationRecViewAdapter() {
    }

    public ConversationRecViewAdapter(ConversationRecViewAdapter.OnConversationLister onConversationLister) {
        this.onConversationLister = onConversationLister;
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
        Message messageRowAt  = latestMessages.get(position);
        String chatNameReceiver;
        if(messageRowAt.getFromId() == FirebaseAuth.getInstance().getUid()){
            chatNameReceiver =  messageRowAt.getToId();
        }else {
            chatNameReceiver =  messageRowAt.getFromId();
        }


        String path  =  "/Users/" +  chatNameReceiver;
        DatabaseReference ref  = FirebaseDatabase.getInstance().getReference(path);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userId  = (String) snapshot.child("userId").getValue().toString();

                String empId =  (String) snapshot.child("empId").getValue().toString();
                String name =  (String) snapshot.child("name").getValue().toString();
                String surname =  (String) snapshot.child("surname").getValue().toString();
                String email =  (String) snapshot.child("email").getValue().toString();
                boolean type =  (boolean) snapshot.child("employeeType").getValue();
                otherUser = new User(userId,empId,name,  surname, email, type);
                String fullName =  otherUser.getName() +  " " +  otherUser.getSurname();
                holder.convUsername.setText(fullName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.latestMessageCov.setText(messageRowAt.getTextMessage());

    }

    @Override
    public int getItemCount() {
        return latestMessages.size();

    }

    public ArrayList<Message> getLatestMessages() {
        return latestMessages;
    }

    public void setLatestMessages(ArrayList<Message> latestMessages) {
        this.latestMessages = latestMessages;
        notifyDataSetChanged();
    }

    public void add (Message message){
        this.latestMessages.add(message);
        notifyDataSetChanged();
    }

    public OnConversationLister getOnConversationLister() {
        return onConversationLister;
    }

    public void setOnConversationLister(OnConversationLister onConversationLister) {
        this.onConversationLister = onConversationLister;
    }

    public User getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
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
