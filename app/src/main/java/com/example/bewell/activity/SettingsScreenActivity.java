package com.example.bewell.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bewell.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsScreenActivity extends AppCompatActivity {

    //Added by navid
    private Button logoOutBtn;
    ///////
    private TextView name, email, password;
    private TextView surname;
    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    private String currentUserId;

    private boolean isAmbassador;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        Intent intent = getIntent();
        isAmbassador =  intent.getBooleanExtra("type", false);


        //DO NOT DELETE CALL AND DEFINITION OF THIS METHOD - USED FOR NAVIGATION BOTTOM
        setUpBottomNavigation();

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);


        name = (TextView) findViewById(R.id.PersonName);
        surname = (TextView) findViewById(R.id.Surname);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        //Log out button - added by navid
        logoOutBtn =  findViewById(R.id.logoutButton);
        logoOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutUser();
            }
        });

        //////








        ValueEventListener eventListener = profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.child("name").getValue().toString();
                    String sur_name = snapshot.child("surname").getValue().toString();
                    String emailID = snapshot.child("email").getValue().toString();
                    //String password_ = snapshot.child("password").getValue().toString();

                    name.setText(username);
                    surname.setText(sur_name);
                    email.setText(emailID);
                    //password.setText(password_);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void update_profile(View view) {

        Intent intent = new Intent(this, UpdateProfile.class);
        startActivity(intent);
    }


    //Set up the bottom navigation
    private void setUpBottomNavigation() {
        BottomNavigationView bottomNavigationView1 = findViewById(R.id.bottomNavigation);
        bottomNavigationView1.setVisibility(View.VISIBLE);
        if (isAmbassador){
            bottomNavigationView1.getMenu().clear();
            bottomNavigationView1.inflateMenu(R.menu.menu_ambassador);


        }else {
            bottomNavigationView1.getMenu().clear();
            bottomNavigationView1.inflateMenu(R.menu.menu_navigation);

        }
        bottomNavigationView1.setSelectedItemId(R.id.SettingsScreenItem);
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
                        return true;

                    case R.id.ConversationAmbassadorScreen:
                        Intent intent =  new Intent(getApplicationContext(), ConversationsScreenActivity.class);
                        intent.putExtra("type", isAmbassador);
                        startActivity(intent);

                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

    }


    //added by navid for testng

    private void logOutUser(){
        mAuth.signOut();
        Intent intent =  new Intent(SettingsScreenActivity.this, LoginScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    ///

}