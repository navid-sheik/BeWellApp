package com.example.bewell.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.bewell.R;
import com.example.bewell.adapters.ConversationRecViewAdapter;
import com.example.bewell.adapters.MessageLogAdapter;
import com.example.bewell.models.User;

import java.util.ArrayList;

public class MessagesScreenActivity extends AppCompatActivity {

    private RecyclerView messageLogRecView;
    private MessageLogAdapter messageLogAdapter;
    private ArrayList<User> messageLogDataServer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_page);

        getSupportActionBar().setTitle("Chat Log");
        initializeViews();
    }



    private void initializeViews (){

        //Recycle View

        messageLogRecView = findViewById(R.id.messageChatLogRecView);
        messageLogAdapter =  new MessageLogAdapter();
        messageLogDataServer.add(new User("message 1", "babd", "dd", "dsds",false));
        messageLogDataServer.add(new User("message 2", "babd", "dd", "dsds",false));
        messageLogDataServer.add(new User("message 3", "babd", "dd", "dsds",false));
        messageLogDataServer.add(new User("message 4", "babd", "dd", "dsds",false));
        messageLogAdapter.setUserMessageLogs(messageLogDataServer);

        messageLogRecView.setAdapter(messageLogAdapter);
        messageLogRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));



    }
}