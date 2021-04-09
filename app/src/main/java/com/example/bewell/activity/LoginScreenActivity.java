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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class LoginScreenActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private EditText usernameLogin;
    private EditText passwordLogin;
    private Button loginBtn;
    private TextView forgotPassTxtView;
    private TextView  goToRegisterTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        initializeValue();

    }


    private void initializeValue(){
        usernameLogin = findViewById(R.id.loginUsername);
        passwordLogin = findViewById(R.id.loginPassword);
        forgotPassTxtView = findViewById(R.id.forgotPassword);
        forgotPassTxtView.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Function not available yet", Toast.LENGTH_SHORT).show());
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(loginPageListener);
        goToRegisterTxtView = findViewById(R.id.goToRegisterTxt);
        goToRegisterTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegistrationPage();
            }
        });
    }

    private View.OnClickListener loginPageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginBtn:
                    loginUser();

            }
        }
    };


    private void loginUser(){
        String userNameString = usernameLogin.getText().toString().trim();
        String passwordString = passwordLogin.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(userNameString, passwordString).addOnCompleteListener(LoginScreenActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Authentication", "signInWithEmail:success");
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
                    Toast.makeText(LoginScreenActivity.this, "Wrong username or password",
                            Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });



    }

    private void goToRegistrationPage(){
        Intent intent = new Intent(LoginScreenActivity.this, PreRegistrationActivity.class);
        startActivity(intent);

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