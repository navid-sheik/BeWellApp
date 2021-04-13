package com.example.bewell.activity;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.bewell.R;
import com.example.bewell.adapters.AmbassadorRecViewAdapter;
import com.example.bewell.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AmbassadorsConversationActivity extends AppCompatActivity implements AmbassadorRecViewAdapter.OnAmbassadorClickListener {
    private RecyclerView  ambassadorRecView;
    private AmbassadorRecViewAdapter amabassadorAdapter;
    private ArrayList<User>  amabassadorsFromServer = new ArrayList<>();

    FirebaseDatabase databaseRef;
    FirebaseUser currentUserLogged;
    User currentUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsiplay_ambassadors_conversation_page);

        currentUser();
        initializeViews();
        fetchUsers ();
    }


    private void initializeViews (){
        ambassadorRecView =  findViewById(R.id.allAmbassadorRecView);

        //Recycle View

        ambassadorRecView = findViewById(R.id.allAmbassadorRecView);
        amabassadorAdapter =  new AmbassadorRecViewAdapter(this);
//        amabassadorsFromServer.add(new User("dcdcd", "name", "babd", "dd", "dsds",false));
//        amabassadorsFromServer.add(new User("sddsjfhdj","name2", "babd", "dd", "dsds",false));
//        amabassadorsFromServer.add(new User("jdbjsabas", "name3", "babd", "dd", "dsds",false));
//        amabassadorsFromServer.add(new User("djhskjdhs", "name4", "babd", "dd", "dsds",false));

        amabassadorAdapter.setAmbassadors(amabassadorsFromServer);


        ambassadorRecView.setAdapter(amabassadorAdapter);
        ambassadorRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));



    }

    private void currentUser(){

            currentUserLogged = FirebaseAuth.getInstance().getCurrentUser();
            String uid  = currentUserLogged.getUid();
            String path   = "Users/" + uid;

            DatabaseReference ref =  FirebaseDatabase.getInstance().getReference(path);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userId  = (String) snapshot.child("userId").getValue().toString();
                    String empId =  (String) snapshot.child("empId").getValue().toString();
                    String name =  (String) snapshot.child("name").getValue().toString();
                    String surname =  (String) snapshot.child("surname").getValue().toString();
                    String email =  (String) snapshot.child("email").getValue().toString();
                    boolean type =  (boolean) snapshot.child("employeeType").getValue();
                    User  currentUserFromServ = new User(userId,empId,name,  surname, email, type);
                    currentUserData =  currentUserFromServ;
                    if (type == false){
                        getSupportActionBar().setTitle("Select Ambassador");
                    }else {
                        getSupportActionBar().setTitle("Select Employee");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    private void fetchUsers (){
        databaseRef =  FirebaseDatabase.getInstance();
        DatabaseReference amabassadorsRef =  FirebaseDatabase.getInstance().getReference("/Users");
        amabassadorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Called when retrive the eliite
                for (DataSnapshot messageSnapshot : snapshot.getChildren()){
                    Log.i("NewMessage",messageSnapshot.getValue().toString());
                    String userId  = (String) messageSnapshot.child("userId").getValue().toString();

                    String empId =  (String) messageSnapshot.child("empId").getValue().toString();
                    String name =  (String) messageSnapshot.child("name").getValue().toString();
                    String surname =  (String) messageSnapshot.child("surname").getValue().toString();
                    String email =  (String) messageSnapshot.child("email").getValue().toString();
                    boolean type =  (boolean) messageSnapshot.child("employeeType").getValue();



                    User  user = new User(userId,empId,name,  surname, email, type);
                    if (currentUserData.isEmployeeType() & !type){
                        amabassadorsFromServer.add(user);
                    }else if (!currentUserData.isEmployeeType() & type){
                        amabassadorsFromServer.add(user);
                    }
//                    amabassadorsFromServer.add(user);

                }
                amabassadorAdapter.setAmbassadors(amabassadorsFromServer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    @Override
    public void onAmbassadorClick(int position) {

        Intent intent  =  new Intent( AmbassadorsConversationActivity.this, MessagesScreenActivity.class);
        intent.putExtra("userToMessage", amabassadorsFromServer.get(position) );
        startActivity(intent);



    }



    @Override
    public void onBackPressed() {
        Log.v("back pressed", "going back");
        super.onBackPressed();
    }
}
