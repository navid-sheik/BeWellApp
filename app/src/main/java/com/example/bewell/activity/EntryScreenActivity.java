package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bewell.R;
import com.example.bewell.entryScreens.AddFood;
import com.example.bewell.putExtra.PutExtra;
import com.example.bewell.adapters.ExerciseRecViewAdapter;
import com.example.bewell.entryScreens.ExerciseEntry;
import com.example.bewell.models.Exercise;
import com.example.bewell.requestCodes.RequestCode;
import com.example.bewell.requestCodes.ResultCode;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EntryScreenActivity extends AppCompatActivity implements ExerciseRecViewAdapter.OnExerciseListener {

    private RecyclerView exerciseRecView;
    private Button addNewFitnessEntryBtn;
    private Context context;
    ExerciseRecViewAdapter adapter;
    private ArrayList<Exercise> userExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_page);
        //Initialize views
        initializeViews();
        //DO NOT DELETE CALL AND DEFINITION OF THIS METHOD - USED FOR NAVIGATION BOTTOM
        setUpBottomNavigation();



    }


    //Functions
    private void initializeViews() {
        exerciseRecView = findViewById(R.id.exerciseRecycleView);
        addNewFitnessEntryBtn = findViewById(R.id.addNewFitnessEntry);
        addNewFitnessEntryBtn.setOnClickListener(buttonOnClickListener);
        context = this;
        //Add mock elements in the list array
        userExercises.add(new Exercise("n", 1, 2, 3, 200));
        userExercises.add(new Exercise("name", 1, 2, 3, 200));
        userExercises.add(new Exercise("tyujj", 1, 2, 3, 200));
        //Adapter set up
        adapter = new ExerciseRecViewAdapter(this);
        adapter.setExercises(userExercises);
        exerciseRecView.setAdapter(adapter);
        exerciseRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    //Set up the bottom navigation
    private void setUpBottomNavigation() {
        BottomNavigationView bottomNavigationView1 = findViewById(R.id.bottomNavigation);
        bottomNavigationView1.setSelectedItemId(R.id.EntryScreenItem);
        bottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.EntryScreenItem:
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
                        startActivity(new Intent(getApplicationContext(), SettingsScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }


    //Button on click listener manager
    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addNewFitnessEntry:
                    goToCreateFitness();
                    break;
                default:
                    break;
            }
        }
    };

    //Add new fitness record button method
    private void goToCreateFitness() {
        Intent addFitnessScreen = new Intent(context, ExerciseEntry.class);
        startActivityForResult(addFitnessScreen, RequestCode.CREATE_NEW_FITNESS_ENTRY);
        //TODO: create constant for intent
    }

    //Get back data from other intent/screen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == RequestCode.CREATE_NEW_FITNESS_ENTRY) && (resultCode == RESULT_OK)) {
            createNewFitnessEntry(data);
        } else if (requestCode == RequestCode.EDIT_EXISTING_FITNESS) {
            int positionToChangeOnArray = data.getIntExtra(PutExtra.POSITION_OF_FITNESS_ENTRY, -1);
            if (resultCode == ResultCode.UPDATE_RECORD) {
                //Exist from the code if position is not returned correctly
                if (positionToChangeOnArray == -1) {
                    return;
                } else {
                    updateExistingFitnessEntry(data, positionToChangeOnArray);
                }
            } else if (resultCode == ResultCode.DELETE_RECORD) {
                if (positionToChangeOnArray == -1) {
                    return;
                } else {
                    deleteFitnessEntry(positionToChangeOnArray);
                }
            }
        }
    }

    //Create a new exercise and add to the array list
    private void createNewFitnessEntry(Intent data) {
        Exercise exerciseToAdd = data.getParcelableExtra(PutExtra.NEW_EXERCISE);
        userExercises.add(exerciseToAdd);
        adapter.setExercises(userExercises);

    }

    //Update an exercise at position passed and pass to the arrayList
    private void updateExistingFitnessEntry(Intent data, int positionArray) {
        Exercise exerciseToEdit = data.getParcelableExtra(PutExtra.UPDATE_EXCERCISE);
        userExercises.set(positionArray, exerciseToEdit);
        adapter.setExercises(userExercises);
    }

    //Delete an exercise at position passed
    private void deleteFitnessEntry(int positionArray) {
        userExercises.remove(positionArray);
        adapter.setExercises(userExercises);
    }

    @Override
    public void onExerciseClick(int position) {
        Toast.makeText(EntryScreenActivity.this, "" + userExercises.get(position), Toast.LENGTH_LONG);
        Intent modifyExerciseScreen = new Intent(EntryScreenActivity.this, ExerciseEntry.class);
        modifyExerciseScreen.putExtra(PutExtra.EXISTING_EXERCISE, userExercises.get(position));
        modifyExerciseScreen.putExtra(PutExtra.POSITION_OF_FITNESS_ENTRY, position);
        startActivityForResult(modifyExerciseScreen, RequestCode.EDIT_EXISTING_FITNESS);
    }
}