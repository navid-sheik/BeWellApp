package com.example.bewell.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bewell.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsScreenActivity extends AppCompatActivity {

    //Added by navid
    private Button logoOutBtn;
    ///////
    private TextView name, email, name2;
    private TextView surname, id_;
    private LinearLayout containerHeightWidth;
    private TextView weightTextView , heightTextFView;
    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    private String currentUserId;
    private ImageButton updateBtn;
    private ImageButton closeActivityButton;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Button resetPasswordBtn;
//    String currentUid = user.getUid();
    ProgressDialog pd;

    private boolean isAmbassador;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        Intent intent = getIntent();
        isAmbassador =  intent.getBooleanExtra("type", false);

        setUpExitButton();
        //DO NOT DELETE CALL AND DEFINITION OF THIS METHOD - USED FOR NAVIGATION BOTTOM
        containerHeightWidth = findViewById(R.id.formWeigthandHeightCobtainer);
        setUpBottomNavigation();
        if (isAmbassador){
            containerHeightWidth.setVisibility(View.INVISIBLE);
        }

        weightTextView =  findViewById(R.id.HeigthTxtView);
        heightTextFView =  findViewById(R.id.weightTxtView);

        resetPasswordBtn = findViewById(R.id.resetPassword);
        resetPasswordBtn.setOnClickListener(settingPageLister);



        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);


        name = (TextView) findViewById(R.id.PersonName);
        surname = (TextView) findViewById(R.id.Surname);
        email = (TextView) findViewById(R.id.email);

        id_ = (TextView) findViewById(R.id.ID) ;
        name2 = (TextView) findViewById(R.id.nameTop);


        //Log out button - added by navid
        logoOutBtn =  findViewById(R.id.logoutButton);
        logoOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutUser();
            }
        });

        //////

        updateBtn = findViewById(R.id.updateButton);
        if(isAmbassador){
            updateBtn.setVisibility(View.INVISIBLE);
        }
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_profile();
            }
        });





        profileUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.child("name").getValue().toString();
                    String sur_name = snapshot.child("surname").getValue().toString();
                    String emailID = snapshot.child("email").getValue().toString();
                    String userId = snapshot.child("empId").getValue().toString();
                    String name_top = snapshot.child("name").getValue().toString();
//                    String password_ = snapshot.child("password").getValue().toString();
                    String weightServer  = snapshot.child("weight").getValue().toString();
                    String heightServer =  snapshot.child("height").getValue().toString();

                    name.setText(username);
                    surname.setText(sur_name);
                    email.setText(emailID);
                    id_.setText(userId);
                    name2.setText(name_top);
                    heightTextFView.setText("Height |" + heightServer + " cm");
                    weightTextView.setText("Weight |" +  weightServer +  " kg");
//                    password.setText(password_);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void setUpExitButton (){
        closeActivityButton = findViewById(R.id.closeButton);
        if (isAmbassador){
            closeActivityButton.setVisibility(View.VISIBLE);
            closeActivityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }


    }

    private View.OnClickListener settingPageLister =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.resetPassword:
                    resetPassword();
                    break;
            }
        }
    };


    private void resetPassword(){
           String emailTxt  = email.getText().toString().trim();
           if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
               Toast.makeText(this, "Email not correct, please try again", Toast.LENGTH_SHORT).show();
               return;
           }

           FirebaseAuth.getInstance().sendPasswordResetEmail(emailTxt).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public void update_profile() {

        String options [] = {"Edit Height", "Edit Weight"};

        pd = new ProgressDialog(SettingsScreenActivity.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsScreenActivity.this);

        builder.setTitle("Choose Options");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    pd.setMessage("Updating Height");
                    showEmailUpdateDialog("height", heightTextFView, "cm");
                }
                else if (which==1){
                    pd.setMessage("Updating Weight");
                    showEmailUpdateDialog("weight", weightTextView, "kg");
                }

            }
        });

        builder.create().show();

//        Intent intent = new Intent(this, Update_Profile.class);
//        startActivity(intent);
    }

    private void showEmailUpdateDialog(String key, TextView textView, String measurements) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsScreenActivity.this);
        builder.setTitle("Update " + key +  " in " + measurements);

        LinearLayout linearLayout = new LinearLayout(SettingsScreenActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10, 10);

        EditText editText = new EditText(SettingsScreenActivity.this);
        editText.setHint("Enter "+key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int unit) {

                String value = editText.getText().toString().trim();

                if (!TextUtils.isEmpty(value)){
                    try{
                        int intValue = Integer.parseInt(value);
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(SettingsScreenActivity.this, "Please enter a numeric value", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    pd.show();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Users").child(uid);
                    ref.child(key).setValue(Integer.parseInt(value))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    textView.setText(key + " |" + value + " " + measurements);
                                    Toast.makeText(SettingsScreenActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SettingsScreenActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                   }

                else{
                    Toast.makeText(SettingsScreenActivity.this, "Enter "+key, Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }





    //Set up the bottom navigation
    private void setUpBottomNavigation() {
        BottomNavigationView bottomNavigationView1 = findViewById(R.id.bottomNavigation);
        if (isAmbassador){
            bottomNavigationView1.setVisibility(View.INVISIBLE);
        }else{
            bottomNavigationView1.setVisibility(View.VISIBLE);
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