package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.bewell.R;
import com.example.bewell.adapters.AmbassadorRecViewAdapter;
import com.example.bewell.adapters.FoodRecViewAdapter;
import com.example.bewell.models.TypeFood;
import com.example.bewell.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xwray.groupie.Item;

import java.util.ArrayList;

public class AmbassadorsConversationActivity extends AppCompatActivity implements AmbassadorRecViewAdapter.OnAmbassadorClickListener {
    private RecyclerView  ambassadorRecView;
    private AmbassadorRecViewAdapter amabassadorAdapter;
    private ArrayList<User>  amabassadorsFromServer = new ArrayList<>();

    FirebaseDatabase databaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsiplay_ambassadors_conversation_page);
        getSupportActionBar().setTitle("Select Ambassador");
        initializeViews();

        fetchUsers ();
    }


    private void initializeViews (){
        ambassadorRecView =  findViewById(R.id.allAmbassadorRecView);

        //Recycle View

        ambassadorRecView = findViewById(R.id.allAmbassadorRecView);
        amabassadorAdapter =  new AmbassadorRecViewAdapter(this);
        amabassadorsFromServer.add(new User("name", "babd", "dd", "dsds",false));
        amabassadorsFromServer.add(new User("name2", "babd", "dd", "dsds",false));
        amabassadorsFromServer.add(new User("name3", "babd", "dd", "dsds",false));
        amabassadorsFromServer.add(new User("name4", "babd", "dd", "dsds",false));
        amabassadorAdapter.setAmbassadors(amabassadorsFromServer);


        ambassadorRecView.setAdapter(amabassadorAdapter);
        ambassadorRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));



    }


    private void fetchUsers (){
        databaseRef =  FirebaseDatabase.getInstance();
        DatabaseReference amabassadorsRef =  FirebaseDatabase.getInstance().getReference("/Users");
        amabassadorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Called when retrive the eliite
                for (DataSnapshot messageSnapshot : snapshot.getChildren()){
                    Log.i("NewMessage",messageSnapshot.getValue().toString());

                    String empId =  (String) messageSnapshot.child("empId").getValue().toString();
                    String name =  (String) messageSnapshot.child("name").getValue().toString();

                    User  user = new User(empId,name,  "fdfd", "dss", false);
                    amabassadorsFromServer.add(user);
                }
                amabassadorAdapter.setAmbassadors(amabassadorsFromServer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    @Override
    public void onAmbassadorClick(int position) {

        Intent intent  =  new Intent( AmbassadorsConversationActivity.this, MessagesScreenActivity.class);
        startActivity(intent);



    }
}
