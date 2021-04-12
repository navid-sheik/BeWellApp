package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.bewell.R;
import com.example.bewell.adapters.AmbassadorRecViewAdapter;
import com.example.bewell.adapters.ConversationRecViewAdapter;
import com.example.bewell.models.Message;
import com.example.bewell.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ConversationsScreenActivity extends AppCompatActivity implements ConversationRecViewAdapter.OnConversationLister {

    private RecyclerView conversationRecView;
    private ConversationRecViewAdapter conversationAdapter;
    private ArrayList<Message> conversationFromServer = new ArrayList<>();
    private FirebaseUser fUser;

    private HashMap<String, Message> latestMessagesHashMap   = new HashMap<>();
    private boolean isAmbassador;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations_page);
        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        isAmbassador =  intent.getBooleanExtra("type", false);
        setUpBottomNavigation();
        initializeViews();
        listenForConversation();

    }



    private void initializeViews (){

        //Recycle View

        conversationRecView = findViewById(R.id.allConversationRecView);
        conversationAdapter =  new ConversationRecViewAdapter(this);

        conversationAdapter.setLatestMessages(conversationFromServer);


        conversationRecView.setAdapter(conversationAdapter);
        conversationRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        conversationRecView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));



    }




    private void listenForConversation (){
        String myUid  =  fUser.getUid();
        String latestMessageFromPath = "/latest-messages/" +  myUid;
        DatabaseReference referenceConv = FirebaseDatabase.getInstance().getReference(latestMessageFromPath);
        referenceConv.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String fromUserId  =  snapshot.child("fromId").getValue().toString();
                String toUserId  =  snapshot.child("toId").getValue().toString();
                String messageId  =  snapshot.child("messageId").getValue().toString();
                String textMessage  =  snapshot.child("textMessage").getValue().toString();
                long timeStamp  = (long) snapshot.child("timeStamp").getValue();
                Message newMessage  =  new Message(messageId,fromUserId,toUserId,timeStamp,textMessage);

                latestMessagesHashMap.put(snapshot.getKey(), newMessage);
                refreshReycleViewMessages();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //Callled everytime when the user called

                String fromUserId  =  snapshot.child("fromId").getValue().toString();
                String toUserId  =  snapshot.child("toId").getValue().toString();
                String messageId  =  snapshot.child("messageId").getValue().toString();
                String textMessage  =  snapshot.child("textMessage").getValue().toString();
                long timeStamp  = (long) snapshot.child("timeStamp").getValue();
                Message newMessage  =  new Message(messageId,fromUserId,toUserId,timeStamp,textMessage);

                latestMessagesHashMap.put(snapshot.getKey(), newMessage);
                refreshReycleViewMessages();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void  refreshReycleViewMessages(){
        ArrayList<Message> newMessagesFromH  =  new ArrayList<>();
        for (String key : latestMessagesHashMap.keySet()){
            newMessagesFromH.add(latestMessagesHashMap.get(key));
        }
        conversationFromServer = newMessagesFromH;
        conversationAdapter.setLatestMessages(conversationFromServer);



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

            case R.id.logoutForAmbassador:
                logoutUser();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private  void logoutUser(){
        mAuth.signOut();
        Intent intent =  new Intent(ConversationsScreenActivity.this, LoginScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);



    }


    private void showAllAmbassador() {
        Intent showAllAmbassadorIntent =  new Intent(ConversationsScreenActivity.this, AmbassadorsConversationActivity.class);
        startActivity(showAllAmbassadorIntent);

    }

    @Override
    public void onConversationClick(int position) {
        User otherUser =  conversationAdapter.getOtherUser();
        Intent intent =  new Intent(ConversationsScreenActivity.this, MessagesScreenActivity.class);
        intent.putExtra("userToMessage", otherUser);
        startActivity(intent);

    }


    //Set up the bottom navigation
    private void setUpBottomNavigation() {
        BottomNavigationView bottomNavigationView1 = findViewById(R.id.bottomNavigation);
        if (isAmbassador){
            bottomNavigationView1.setVisibility(View.VISIBLE);
            bottomNavigationView1.getMenu().clear();
            bottomNavigationView1.inflateMenu(R.menu.menu_ambassador);
            bottomNavigationView1.setBackgroundColor(bottomNavigationView1.getItemBackground().getAlpha());
//            bottomNavigationView1.setItemTextColor(ColorStateList.valueOf(R.drawable.selector));
//            bottomNavigationView1.setItemIconTintList(ColorStateList.valueOf(R.drawable.selector));
//            bottomNavigationView1.setItemIconTintList(ColorStateList.valueOf(R.color.gray));
            // TODO WHEN

        }else {
            bottomNavigationView1.getMenu().clear();
            bottomNavigationView1.setVisibility(View.INVISIBLE);


        }
        bottomNavigationView1.setSelectedItemId(R.id.ConversationAmbassadorScreen);
        bottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.EntryScreenItem:
                        startActivity(new Intent(getApplicationContext(), EntryScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.HomeScreenItem:
                        startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.HelpScreenItem:
                        startActivity(new Intent(getApplicationContext(), HelpScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.SettingsScreenItem:
                        Intent intent =  new Intent(getApplicationContext(), SettingsScreenActivity.class);
                        intent.putExtra("type", isAmbassador);
                        startActivity(intent);

                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.ConversationAmbassadorScreen:

                        return true;

                }
                return false;
            }
        });
    }

}