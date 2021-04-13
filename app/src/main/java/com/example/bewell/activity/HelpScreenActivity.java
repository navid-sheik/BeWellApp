package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.bewell.R;
import com.example.bewell.adapters.BlogRecViewAdapter;
import com.example.bewell.models.Blog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HelpScreenActivity extends AppCompatActivity implements BlogRecViewAdapter.OnBlogListener {


    private Button conversationBtn;
    private Button showAllAmbassadorBtn;
    private FirebaseAuth mAuth;;
    private RecyclerView mentalHealhtRecView;
    private RecyclerView fitnessHealthRecView;
    private RecyclerView mealRecView;
    private BlogRecViewAdapter blogAdapter;
    private ArrayList<Blog>  blogsFromServer  =   new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        mAuth =  FirebaseAuth.getInstance();

        //DO NOT DELETE CALL AND DEFINITION OF THIS METHOD - USED FOR NAVIGATION BOTTOM
        setUpBottomNavigation();
        setUpViews();
    }


    private void setUpViews (){
        conversationBtn =  findViewById(R.id.conversationBtn);
        showAllAmbassadorBtn = findViewById(R.id.ambassadorListBtn);
        conversationBtn.setOnClickListener(helpPageButtonListener);
        showAllAmbassadorBtn.setOnClickListener(helpPageButtonListener);

        mentalHealhtRecView =  findViewById(R.id.meantalHealthBlogRecycleView);
        fitnessHealthRecView =  findViewById(R.id.fitnessBlogRecViews);
        mealRecView = findViewById(R.id.MealBlogRecViews);


        blogAdapter =  new BlogRecViewAdapter(this);
        blogsFromServer.add(new Blog("Title placeholder for blog"));
        blogsFromServer.add(new Blog("Title placeholder for blog 2"));
        blogsFromServer.add(new Blog("Title placeholder for blog 3"));

        blogAdapter.setBlogs(blogsFromServer);


        mentalHealhtRecView.setAdapter(blogAdapter);
        fitnessHealthRecView.setAdapter(blogAdapter);
        mealRecView.setAdapter(blogAdapter);

        mentalHealhtRecView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        fitnessHealthRecView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        mealRecView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
    }


    private View.OnClickListener helpPageButtonListener  =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.conversationBtn:
                    showAllConversation();
                    break;
                case R.id.ambassadorListBtn:
                    showAllAmbassador();
                    break;

            }
        }
    };


    private void showAllConversation(){
        Intent  conversationIntent =  new Intent(HelpScreenActivity.this, ConversationsScreenActivity.class);
        startActivity(conversationIntent);
    }

    private void showAllAmbassador(){
//        Intent showAllAmbassadorIntent =  new Intent(HelpScreenActivity.this, AmbassadorsConversationActivity.class);
//        startActivity(showAllAmbassadorIntent);

        mAuth.signOut();
        Intent intent =  new Intent(HelpScreenActivity.this, LoginScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    //Set up the bottom navigation
    private void setUpBottomNavigation() {
        BottomNavigationView bottomNavigationView1 = findViewById(R.id.bottomNavigation);
        bottomNavigationView1.setSelectedItemId(R.id.HelpScreenItem);
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
                        return true;
                    case R.id.SettingsScreenItem:
                        startActivity(new Intent(getApplicationContext(), SettingsScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onBlogClick(int position) {

    }
}