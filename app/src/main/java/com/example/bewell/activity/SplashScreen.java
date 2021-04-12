package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.bewell.R;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    Handler handler;
    private String uid;
    private FirebaseUser currentUserLogged;
    private boolean isAmbassador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        verifyUserLogIn();
        getUserDataLoggedIn();





    }


    //Verify loggegd - DO NOT DELETE

    private void verifyUserLogIn (){
        uid = FirebaseAuth.getInstance().getUid();
        if (uid == null){
            Intent intent =  new Intent(SplashScreen.this, LoginScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    private void getUserDataLoggedIn(){
        currentUserLogged = FirebaseAuth.getInstance().getCurrentUser();
        String uid  = currentUserLogged.getUid();
        String path   = "Users/" + uid;

        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference(path);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String userId  = (String) snapshot.child("userId").getValue().toString();
//                String empId =  (String) snapshot.child("empId").getValue().toString();
//                String name =  (String) snapshot.child("name").getValue().toString();
//                String surname =  (String) snapshot.child("surname").getValue().toString();
//                String email =  (String) snapshot.child("email").getValue().toString();
                boolean type =  (boolean) snapshot.child("employeeType").getValue();
//                User currentUserFromServ = new User(userId,empId,name,  surname, email, type);
                isAmbassador =  type;
                slapshToAnotherScreen(isAmbassador);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void  slapshToAnotherScreen (boolean isAmbassador){

        handler =  new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (isAmbassador){

                    Intent intent = new Intent(SplashScreen.this, ConversationsScreenActivity.class);
                    intent.putExtra("type", isAmbassador);
                    intent.putExtra("userInfo",user);
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(SplashScreen.this, HomeScreenActivity.class);
                    intent.putExtra("type", isAmbassador);
                    intent.putExtra("userInfo",user);
                    startActivity(intent);
                }

                finish();
            }
        },1000);
    }
}