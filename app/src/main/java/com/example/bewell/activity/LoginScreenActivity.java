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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bewell.R;
import com.example.bewell.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText editEmail, editPassword;
    private Button loginButton;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        loginButton = (Button) findViewById((R.id.firebaseLoginButton));
        loginButton.setOnClickListener(this);

        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById((R.id.password));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegistrationScreenActivity.class));
                break;

            case R.id.firebaseLoginButton:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(email.isEmpty()){
            editEmail.setError("The Email is required to login.");
            editEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please enter a valid email to login.");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editPassword.setError("Password is required to sign in");
            editPassword.requestFocus();
            return;
        }

        if(password.length() <6){
            editPassword.setError("Minimum password is 6 characters");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    //redirect to users profile
                    startActivity(new Intent(LoginScreenActivity.this, HomeScreenActivity.class));
                }else{
                    Toast.makeText(LoginScreenActivity.this, "Failed to login, Please check your email and password and try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //This page is for testing firebase || use as reference in the real singleton

    /* FirebaseAuth mAuth;
    FirebaseDatabase database;
    TextInputEditText randomTextFiels;

    TextInputEditText randomPassField;
    Button randomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        randomTextFiels  =  findViewById(R.id.randomTxtField);
        randomPassField   = findViewById(R.id.randomPasswordField);
        randomButton   = findViewById(R.id.testFirebaseButton);
        mAuth = FirebaseAuth.getInstance();



        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt =  randomTextFiels.getText().toString().trim();
                String pass =  randomPassField.getText().toString().trim();



                    mAuth.createUserWithEmailAndPassword(txt, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           Log.v("SucesssAuth",  "Success");
                            if (task.isSuccessful()){




                                User user  = new User(txt, pass);
                                Log.d("user1", "error");

                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){
                                                    Toast.makeText(LoginScreenActivity.this,"User been registred correctly", Toast.LENGTH_SHORT);
                                                    Intent intent  =  new Intent(LoginScreenActivity.this, HomeScreenActivity.class);
                                                    startActivity(intent);
                                                }else{
                                                    Log.v("Database", "wrong staff");
                                                }
                                            }

                                        });
                            }else {
                                Log.v("Wrong login", "wrong staff");
                                Toast.makeText(LoginScreenActivity.this,"Error", Toast.LENGTH_SHORT);

                            }
                        }
                    });
                }


        });
    }

*/
}