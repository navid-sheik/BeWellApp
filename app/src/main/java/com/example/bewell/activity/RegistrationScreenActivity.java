package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bewell.R;
import com.example.bewell.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class  RegistrationScreenActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    private CheckBox terms;
    private Button signUpBtn;

    private EditText employeeID;
    private EditText nameV;
    private EditText surnameV;
    private EditText emailV;
    private EditText passwordV;
    private EditText password2V;

    Boolean typeEmployee;

    private int defaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        mAuth = FirebaseAuth.getInstance();
        initializeValue();

    }

    private void initializeValue(){
        terms = findViewById(R.id.terms);
        employeeID = findViewById(R.id.employeeId);
        employeeID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                employeeID.setError(null);
                employeeID.setTextColor(defaultColor);
                signUpBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nameV= findViewById(R.id.registerName);
        nameV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameV.setError(null);
                nameV.setTextColor(defaultColor);
                signUpBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        surnameV = findViewById(R.id.registerSurname);
        surnameV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                surnameV.setError(null);
                surnameV.setTextColor(defaultColor);
                signUpBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailV = findViewById(R.id.registerEmail);
        emailV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailV.setError(null);
                emailV.setTextColor(defaultColor);
                signUpBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordV = findViewById(R.id.resgisterPassword);
        passwordV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordV.setError(null);
                passwordV.setTextColor(defaultColor);
                signUpBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password2V = findViewById(R.id.registerRepeatPassword);
        password2V.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password2V.setError(null);
                password2V.setTextColor(defaultColor);
                signUpBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signUpBtn = findViewById(R.id.signUpButton);
        signUpBtn.setOnClickListener(regiStrationButtonListener);

        ColorStateList colorList = emailV.getTextColors();
        defaultColor = colorList.getDefaultColor();
    }


    private View.OnClickListener regiStrationButtonListener  =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case  R.id.signUpButton:
                    createUser();
            }
        }
    };


    private void createUser(){
        String id = employeeID.getText().toString().trim();
        String name = nameV.getText().toString().trim();
        String surname = surnameV.getText().toString().trim();
        String email = emailV.getText().toString().trim();
        String password = passwordV.getText().toString().trim();
        String password2 = password2V.getText().toString().trim();

        typeEmployee = getIntent().getExtras().getBoolean("type");


        if (fieldValidation(id,name,surname,email,password,password2)){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.v("SucesssAuth",  "Success");
                    if (task.isSuccessful()){
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        User user  = new User(userId,id,name,surname,email,typeEmployee);

                        DatabaseReference refEmployee   =  FirebaseDatabase.getInstance().getReference("Users");
                        DatabaseReference refAmabassador  =  FirebaseDatabase.getInstance().getReference("Ambassdor ");

                        FirebaseDatabase.getInstance().getReference("Users").child(userId).setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(RegistrationScreenActivity.this,"User data has been  registered correctly", Toast.LENGTH_SHORT);
                                            Intent intent  =  new Intent(RegistrationScreenActivity.this, SplashScreen.class);
                                            intent.putExtra("type", typeEmployee);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }else{
                                            Log.v("Database", "Something went wrong during database  ");
                                        }
                                    }

                                });
                    } else {
                        Log.v("Authentication", "Problems with email or password , it may be to short or not complex enough");
                        Toast.makeText(RegistrationScreenActivity.this,"Error during login", Toast.LENGTH_SHORT);
                    }
                }
            });
        }

    }
    private boolean fieldValidation(String id,String name,String surname,String email,String password,String password2){
        setOkView(employeeID);
        setOkView(nameV);
        setOkView(surnameV);
        setOkView(emailV);
        setOkView(passwordV);
        setOkView(password2V);


        final String Expn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        if(id.isEmpty()) {
            setErrorView(employeeID,"Id cannot be empty");
        }
        else if(name.isEmpty()){
            setErrorView(nameV,"Name must not be empty");
        }
        else if(surname.isEmpty()){
            setErrorView(surnameV,"Surname must not be empty");
        }
        else if(!email.matches(Expn)){
            setErrorView(emailV,"Enter a valid email");
            }
        else if(password.length() < 7 || !password.matches(".*\\d.*")){
            setErrorView(passwordV,"Password must be at least 7 characters long and contain a number");
            //return;
        }
        else if(!password.equals(password2)){
            setErrorView(password2V,"Passwords must match");
        }
        else if(!terms.isChecked()) {
            Toast.makeText(getApplicationContext(), "You need to accept terms and conditions first", Toast.LENGTH_SHORT).show();
            //return;
        }
        else{
            return true;
        }
        return false;
    }
    private void setOkView(EditText text) {
        text.setError(null);
        text.setTextColor(defaultColor);
        signUpBtn.setEnabled(true);
    }

    private void setErrorView(EditText text,String message) {
        text.setError(message);
        signUpBtn.setEnabled(false);
        text.setTextColor(Color.RED);
    }
}
