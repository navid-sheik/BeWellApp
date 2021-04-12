package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bewell.R;
import com.example.bewell.adapters.ConversationRecViewAdapter;
import com.example.bewell.adapters.MessageLogAdapter;
import com.example.bewell.models.Message;
import com.example.bewell.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessagesScreenActivity extends AppCompatActivity {

    private RecyclerView messageLogRecView;
    private MessageLogAdapter messageLogAdapter;
    private ArrayList<Message> messageLogDataServer = new ArrayList<>();
    private User userToMessageObject;
    private String userIdToMessage;
    private String myUserId;
    private FirebaseUser currentUserAuth;
    private Button sendMessageBtn;
    private EditText messageTxtField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_page);
        Intent intent = getIntent();
        userToMessageObject =  intent.getParcelableExtra("userToMessage");
        getSupportActionBar().setTitle(userToMessageObject.getName());
        currentUserAuth =  FirebaseAuth.getInstance().getCurrentUser();
        myUserId =  currentUserAuth.getUid();
        userIdToMessage =  userToMessageObject.getUserId();
        initializeViews();
        listenForMessage();
    }



    private void initializeViews (){
        //Button and textfield
        sendMessageBtn =  findViewById(R.id.sendMessage);
        sendMessageBtn.setOnClickListener(messagePageClickListener);
        messageTxtField =  findViewById(R.id.messageEntered);



        //Recycle View

        messageLogRecView = findViewById(R.id.messageChatLogRecView);
        messageLogAdapter =  new MessageLogAdapter(this);



        messageLogAdapter.setUserMessageLogs(messageLogDataServer);

        messageLogRecView.setAdapter(messageLogAdapter);
        messageLogRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));



    }


    private View.OnClickListener messagePageClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sendMessage:
                    sendMessageToServer();
                    break;
            }
        }
    };




    private void listenForMessage  (){
        String pathUserFrom = "/messages/" + myUserId + "/" + userIdToMessage;
        DatabaseReference  ref = FirebaseDatabase.getInstance().getReference(pathUserFrom);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
             public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
                        String fromUserId  =  snapshot.child("fromId").getValue().toString();
                        String toUserId  =  snapshot.child("toId").getValue().toString();
                        String messageId  =  snapshot.child("messageId").getValue().toString();
                         String textMessage  =  snapshot.child("textMessage").getValue().toString();
                         long timeStamp  = (long) snapshot.child("timeStamp").getValue();
                         Message newMessage  =  new Message(messageId,fromUserId,toUserId,timeStamp,textMessage);
                        messageLogDataServer.add(newMessage);
                        messageLogAdapter.setUserMessageLogs(messageLogDataServer);
//                    messageLogDataServer.add(newMessage);
//                    messageLogAdapter.setUserMessageLogs(messageLogDataServer);
                        messageLogRecView.scrollToPosition(messageLogAdapter.getItemCount() - 1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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




    private void sendMessageToServer() {

        String messageContent = messageTxtField.getText().toString();
        String pathUserFrom = "/messages/" + myUserId + "/" + userIdToMessage;
        String pathUserTo =   "/messages/" + userIdToMessage + "/" + myUserId;

        String pathLatestMessageFrom = "/latest-messages/" + myUserId + "/" + userIdToMessage;
        String pathLatestMessageTo =   "/latest-messages/" + userIdToMessage + "/" + myUserId;


        DatabaseReference databaseRef  = FirebaseDatabase.getInstance().getReference(pathUserFrom).push();
        DatabaseReference databaseRefTo  = FirebaseDatabase.getInstance().getReference(pathUserTo).push();
        DatabaseReference databaseLatestFromRef  = FirebaseDatabase.getInstance().getReference(pathLatestMessageFrom);
        DatabaseReference databaseLatestToRef  = FirebaseDatabase.getInstance().getReference(pathLatestMessageTo);
        Long timeStamp  =  System.currentTimeMillis() / 1000;
        Message message = new  Message (databaseRef.getKey(),myUserId, userIdToMessage, timeStamp, messageContent );
        databaseRef.setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                messageTxtField.setText("");
                Log.v("Message",  "Successs made");
                messageLogRecView.scrollToPosition(messageLogAdapter.getItemCount() - 1);
            }
        });





        databaseRefTo.setValue(message);


        databaseLatestFromRef.setValue(message);
        databaseLatestToRef.setValue(message);




    }
}