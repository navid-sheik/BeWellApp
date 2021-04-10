package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.bewell.R;
import com.example.bewell.adapters.AmbassadorRecViewAdapter;
import com.example.bewell.adapters.ConversationRecViewAdapter;
import com.example.bewell.models.User;

import java.util.ArrayList;

public class ConversationsScreenActivity extends AppCompatActivity implements ConversationRecViewAdapter.OnConversationLister {

    private RecyclerView conversationRecView;
    private ConversationRecViewAdapter conversationAdapter;
    private ArrayList<User> conversationFromServer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations_page);
        initializeViews();

    }



    private void initializeViews (){

        //Recycle View

        conversationRecView = findViewById(R.id.allConversationRecView);
        conversationAdapter =  new ConversationRecViewAdapter(this);
        conversationFromServer.add(new User("conv1", "babd", "dd", "dsds",false));
        conversationFromServer.add(new User("conv2", "babd", "dd", "dsds",false));
        conversationFromServer.add(new User("conv3", "babd", "dd", "dsds",false));
        conversationFromServer.add(new User("conv4", "babd", "dd", "dsds",false));
        conversationAdapter.setAmbassadorContacts(conversationFromServer);


        conversationRecView.setAdapter(conversationAdapter);
        conversationRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.menu_messaging, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_createNewConversation:
                showAllAmbassador();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showAllAmbassador() {
        Intent showAllAmbassadorIntent =  new Intent(ConversationsScreenActivity.this, AmbassadorsConversationActivity.class);
        startActivity(showAllAmbassadorIntent);

    }

    @Override
    public void onConversationClick(int position) {

    }
}