package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bewell.R;
import com.example.bewell.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class LoginScreenActivity extends AppCompatActivity {

    //This page is for testing firebase || use as reference in the real singleton

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    /*TextInputEditText randomTextFiels;

    TextInputEditText randomPassField;
    Button randomButton;*/
    private EditText name;
    private EditText password;
    //private EditText info;
    private Button login;
    private TextView forgot;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();

        /*randomTextFiels  =  findViewById(R.id.randomTxtField);
        randomPassField   = findViewById(R.id.randomPasswordField);
        randomButton   = findViewById(R.id.testFirebaseButton);*/


        //getSupportActionBar().setDisplayShowTitleEnabled(false);


        name = findViewById(R.id.userText);
        password = findViewById(R.id.passText);
        forgot = findViewById(R.id.forgot);
        forgot.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Function not available yet", Toast.LENGTH_SHORT).show());
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = name.getText().toString().trim();
                String pass = password.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(usr, pass).addOnCompleteListener(LoginScreenActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("auth", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //get object for user
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase.getInstance().getReference().child("Users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", task.getException());
                                    }
                                    else {
                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                    }
                                }
                            });
                            Intent intent = new Intent(LoginScreenActivity.this, HomeScreenActivity.class);
                            intent.putExtra("userInfo",user);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("auth", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginScreenActivity.this, "Wrong user or password",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }


        });
        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreenActivity.this, PreRegistrationActivity.class);
            startActivity(intent);
        });




    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //go to home page
        }

    }
}