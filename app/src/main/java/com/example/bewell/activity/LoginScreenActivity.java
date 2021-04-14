package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreenActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private EditText usernameLogin;
    private EditText passwordLogin;
    private Button loginBtn;
    private TextView forgotPassTxtView;
    private TextView  goToRegisterTxtView;
    private int defaultColor;
    boolean isAmbassador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        //Cheeck if it's an mabassdor / default it's will retrive the type when logged

        initializeValue();

    }


    private void initializeValue(){
        //username textview init
        usernameLogin = findViewById(R.id.loginUsername);

        ColorStateList colorList = usernameLogin.getTextColors();
        defaultColor = colorList.getDefaultColor();


        usernameLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                usernameLogin.setError(null);
                usernameLogin.setTextColor(defaultColor);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String loginString = usernameLogin.getText().toString();
                if (loginString.isEmpty()) {
                    setErrorView(usernameLogin);
                } else {
                    setOkView(usernameLogin);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        //password textview init

        passwordLogin = findViewById(R.id.loginPassword);
        passwordLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                passwordLogin.setError(null);
                passwordLogin.setTextColor(defaultColor);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passwordString = passwordLogin.getText().toString();
                if (passwordString.isEmpty()) {
                    setErrorView(passwordLogin);
                } else {
                    setOkView(passwordLogin);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        forgotPassTxtView = findViewById(R.id.forgotPassword);
        forgotPassTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });



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

    public void resetPassword(){
        final String Expn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter your email");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);


        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String forgotEmailString = input.getText().toString();
                if(!forgotEmailString.matches(Expn)){
                    Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseAuth.getInstance().sendPasswordResetEmail(forgotEmailString).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Success , check your email address ", Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(getApplicationContext(), "Problem , something went wrong ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();


    }

    private void setOkView(EditText text) {
        text.setError(null);
        text.setTextColor(defaultColor);
        loginBtn.setEnabled(true);
    }

    private void setErrorView(EditText text) {
        text.setError("Field cannot be empty");
        loginBtn.setEnabled(false);
        text.setTextColor(Color.RED);
    }

    private View.OnClickListener loginPageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginBtn:
                    if(usernameLogin.getText().toString().isEmpty()){
                        setErrorView(usernameLogin);
                    }
                    else if(passwordLogin.getText().toString().isEmpty()){
                        setErrorView(passwordLogin);
                    }
                    else{
                        loginUser();
                    }


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
                    String path =  "/Users/" +   userId;
                    DatabaseReference  ref  =  FirebaseDatabase.getInstance().getReference(path);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            isAmbassador =  (boolean) snapshot.child("employeeType").getValue();
                            Intent intent = new Intent(LoginScreenActivity.this, SplashScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




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
    private void emptyChecker(TextView blank,int length){
        if(length > 0){
            blank.setError("Fill in this field");
            //blank.setEnabled(false);
            //blank.setTextColor(Color.RED);
            //blank.getBackground().setAlpha(128);

        }
        else{

        }
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