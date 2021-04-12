package com.example.bewell.adapters;

import android.content.Context;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MessageLogAdapter extends RecyclerView.Adapter<MessageLogAdapter.ViewHolder> {

    private ArrayList<Message> userMessageLogs = new ArrayList<>();
    private static  final int MGS_TYPE_FROM  = 0;
    private static  final int MGS_TYPE_TO = 1;
    Context context;
    FirebaseUser fUser;




    public MessageLogAdapter(Context context) {
        this.context  = context;
    }



    @NonNull
    @Override
    public MessageLogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MGS_TYPE_FROM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message_from, parent, false);
            MessageLogAdapter.ViewHolder holder = new MessageLogAdapter.ViewHolder(view);
            return holder;

        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message_to, parent, false);
            MessageLogAdapter.ViewHolder holder = new MessageLogAdapter.ViewHolder(view);
            return holder;

        }




    }



    @Override
    public void onBindViewHolder(@NonNull MessageLogAdapter.ViewHolder holder, int position) {
        String message = userMessageLogs.get(position).getTextMessage();
        holder.message.setText(message);

    }

    @Override
    public int getItemCount() {
        return userMessageLogs.size();

    }

    public void setUserMessageLogs(ArrayList<Message> userMessageLogs) {
        this.userMessageLogs = userMessageLogs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView message;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message  =  itemView.findViewById(R.id.usernameMessage);


        }


    }

    @Override
    public int getItemViewType(int position) {
        fUser  = FirebaseAuth.getInstance().getCurrentUser();
        if (userMessageLogs.get(position).getFromId().equals(fUser.getUid())){
            return MGS_TYPE_FROM;
        }else{
            return MGS_TYPE_TO;
        }







    }
}
