package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        mAuth = FirebaseAuth.getInstance();
        initiliazeValue();

    }

    private  void initiliazeValue(){
        terms = findViewById(R.id.terms);
        employeeID = findViewById(R.id.employeeId);
        nameV= findViewById(R.id.registerName);
        surnameV = findViewById(R.id.registerSurname);
        emailV = findViewById(R.id.registerEmail);
        passwordV = findViewById(R.id.resgisterPassword);
        password2V = findViewById(R.id.registerRepeatPassword);
        signUpBtn = findViewById(R.id.signUpButton);
        signUpBtn.setOnClickListener(regiStrationButtonListener);

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
        final String Expn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        if(!terms.isChecked()) {
            Toast.makeText(getApplicationContext(), "You need to accept terms and conditions first", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(email.isEmpty() || id.isEmpty() || name.isEmpty() || surname.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please complete the form first", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(!email.matches(Expn)){
            Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(password.length() < 7 || !password.matches(".*\\d.*")){
            Toast.makeText(getApplicationContext(), "Password must be at least 6 characters long and include a number", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password.equals(password2)){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.v("SucesssAuth",  "Success");
                    if (task.isSuccessful()){
                        User user  = new User(id,name,surname,email,typeEmployee);
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase.getInstance().getReference("Users").child(userId).setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(RegistrationScreenActivity.this,"User data has been  registered correctly", Toast.LENGTH_SHORT);
                                            Intent intent  =  new Intent(RegistrationScreenActivity.this, HomeScreenActivity.class);
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
        else {
            Toast.makeText(RegistrationScreenActivity.this, "Passwords do not match",
                    Toast.LENGTH_SHORT).show();
        }

    }


}
