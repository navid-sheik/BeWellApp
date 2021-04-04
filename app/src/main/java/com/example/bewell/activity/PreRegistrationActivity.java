package com.example.bewell.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.bewell.R;

public class PreRegistrationActivity extends AppCompatActivity {
    private Button employee;
    private Button ambassador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_registration_page);

        employee = findViewById(R.id.employeeOption);
        ambassador = findViewById(R.id.ambassadorOption);

        employee.setOnClickListener(v -> {
            Intent intent = new Intent(PreRegistrationActivity.this, RegistrationScreenActivity.class);
            intent.putExtra("type",false);
            startActivity(intent);
        });
        ambassador.setOnClickListener(v -> {
            Intent intent = new Intent(PreRegistrationActivity.this, RegistrationScreenActivity.class);
            intent.putExtra("type",true);
            startActivity(intent);
        });
    }
}