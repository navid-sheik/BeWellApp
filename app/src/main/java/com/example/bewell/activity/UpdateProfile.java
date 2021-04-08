package com.example.bewell.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bewell.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class UpdateProfile extends AppCompatActivity {

    Button button;
    EditText name, surname, email, password;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUid = user.getUid();

    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        documentReference = db.collection("user").document(currentUid);
        setContentView(R.layout.update_profile);
        name = findViewById(R.id.edit_name);
        surname = findViewById(R.id.edit_surname);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.getResult().exists()){
                            String name_ = task.getResult().getString("name");
                            String surname_ = task.getResult().getString("surname");
                            String email_ = task.getResult().getString("email");
                            String password_ = task.getResult().getString("password");

                            name.setText(name_);
                            surname.setText(surname_);
                            email.setText(email_);
                            password.setText(password_);
                        }
                        else {
                            Toast.makeText(UpdateProfile.this, "No profile found", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    private void updateProfile (){
        String new_name = name.getText().toString();
        String new_surname = surname.getText().toString();
        String new_email = email.getText().toString();
        String new_password = password.getText().toString();

        final DocumentReference sDoc = db.collection("user").document(currentUid);

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {

                transaction.update(sDoc, "name", new_name);
                transaction.update(sDoc, "surname", new_surname);
                transaction.update(sDoc, "email", new_email);
                transaction.update(sDoc, "password", new_password);


                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

