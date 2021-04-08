package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bewell.R;
import com.example.bewell.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class
RegistrationScreenActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    private CheckBox terms;
    private Button signUp;

    private EditText idV;
    private EditText nameV;
    private EditText surnameV;
    private EditText emailV;
    private EditText passwordV;
    private EditText password2V;

    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        mAuth = FirebaseAuth.getInstance();
        terms = findViewById(R.id.terms);

        idV = findViewById(R.id.employeeId);
        nameV= findViewById(R.id.registerName);
        surnameV = findViewById(R.id.registerSurname);
        emailV = findViewById(R.id.registerEmail);
        passwordV = findViewById(R.id.resgisterPassword);
        password2V = findViewById(R.id.registerRepeatPassword);



        signUp = findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idV.getText().toString().trim();
                String name = nameV.getText().toString().trim();
                String surname = surnameV.getText().toString().trim();
                String email = emailV.getText().toString().trim();
                String password = passwordV.getText().toString().trim();
                String password2 = password2V.getText().toString().trim();
                boolean type = getIntent().getExtras().getBoolean("type");
                if(!terms.isChecked()) {
                    Toast.makeText(getApplicationContext(), "You need to accept terms and conditions first", Toast.LENGTH_SHORT).show();
                }
                else if(password.length() < 7 || !password.matches(".*\\d.*")){
                    Toast.makeText(getApplicationContext(), "Password must be at least 6 characters long and include a number", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals(password2)){
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.v("SucesssAuth",  "Success");
                            if (task.isSuccessful()){
                                User user  = new User(name, surname,id,type);
                                Log.d("user1", "error");
                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase.getInstance().getReference("Users").child(userId).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){
                                                    Toast.makeText(RegistrationScreenActivity.this,"User been registered correctly", Toast.LENGTH_SHORT);
                                                    Intent intent  =  new Intent(RegistrationScreenActivity.this, HomeScreenActivity.class);
                                                    startActivity(intent);
                                                }else{
                                                    Log.v("Database", "wrong staff");
                                                }
                                            }

                                        });
                            }
                            else {
                                Log.v("Wrong login", "wrong staff");
                                Toast.makeText(RegistrationScreenActivity.this,"Error", Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(RegistrationScreenActivity.this, "Passwords do not match",
                            Toast.LENGTH_SHORT).show();
                }
            }


        });






    }

    //Set up the bottom navigation
    private void setUpBottomNavigation() {
        BottomNavigationView bottomNavigationView1 = findViewById(R.id.bottomNavigation);
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

                }
                return false;
            }
        });
    }

}
