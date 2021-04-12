package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bewell.R;
import com.example.bewell.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.hardware.SensorManager;

public class HomeScreenActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    TextView stepCounter;

    private String uid;
    private TextView randomTextView;

    boolean running = false;

    private boolean isAmbassador;
    private FirebaseUser currentUserLogged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent = getIntent();
        isAmbassador =  intent.getBooleanExtra("type", false);

        randomTextView  =  findViewById(R.id.userIdSample);
        uid = FirebaseAuth.getInstance().getUid();
        randomTextView.setText(uid);
        //verifyUserLogIn();

        //DO NOT DELETE CALL AND DEFINITION OF THIS METHOD - USED FOR NAVIGATION BOTTOM

        setUpBottomNavigation();

        //DO NOT DELETE CALL AND DEFINITION OF THIS METHOD - USED FOR NAVIGATION BOTTOM


        stepCounter = (TextView) findViewById(R.id.stepCounter);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }


    //Verify loggegd - DO NOT DELETE

//    private void verifyUserLogIn (){
//
//
//        if (uid == null){
//            Intent intent =  new Intent(HomeScreenActivity.this, LoginScreenActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
////        getUserDataLoggedIn();
//
//        Toast.makeText(HomeScreenActivity.this, "Uid is" + uid, Toast.LENGTH_LONG);
//
//    }

//    private void getUserDataLoggedIn(){
//        currentUserLogged = FirebaseAuth.getInstance().getCurrentUser();
//        String uid  = currentUserLogged.getUid();
//        String path   = "Users/" + uid;
//
//        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference(path);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                String userId  = (String) snapshot.child("userId").getValue().toString();
////                String empId =  (String) snapshot.child("empId").getValue().toString();
////                String name =  (String) snapshot.child("name").getValue().toString();
////                String surname =  (String) snapshot.child("surname").getValue().toString();
////                String email =  (String) snapshot.child("email").getValue().toString();
//                boolean type =  (boolean) snapshot.child("employeeType").getValue();
////                User currentUserFromServ = new User(userId,empId,name,  surname, email, type);
//                isAmbassador =  type;
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        //sensorManager.unregisterListener(this);
    }

    //Set up the bottom navigation
    private void setUpBottomNavigation() {
        BottomNavigationView bottomNavigationView1 = findViewById(R.id.bottomNavigation);
//        if (isAmbassador){
//            bottomNavigationView1.getMenu().clear();
//            bottomNavigationView1.inflateMenu(R.menu.menu_ambassador);
//
//        }else {
//            bottomNavigationView1.getMenu().clear();
//            bottomNavigationView1.inflateMenu(R.menu.menu_navigation);
//
//        }

        bottomNavigationView1.setSelectedItemId(R.id.HomeScreenItem);
        bottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.EntryScreenItem:
                        startActivity(new Intent(getApplicationContext(), EntryScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.HomeScreenItem:
                        return true;
                    case R.id.HelpScreenItem:
                        startActivity(new Intent(getApplicationContext(), HelpScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.SettingsScreenItem:
                        startActivity(new Intent(getApplicationContext(), SettingsScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.ConversationAmbassadorScreen:
                        startActivity(new Intent(getApplicationContext(), ConversationsScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running){
            stepCounter.setText(String.valueOf(event.values[0]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

}