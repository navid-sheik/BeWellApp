package com.example.bewell.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bewell.R;
import com.example.bewell.adapters.FoodRecViewAdapter;
import com.example.bewell.adapters.MoodQuestionsAdapter;
import com.example.bewell.entryScreens.AddFood;
import com.example.bewell.models.Food;
import com.example.bewell.models.MoodOfTheDay;
import com.example.bewell.models.MoodQuestion;
import com.example.bewell.models.TypeFood;
import com.example.bewell.models.TypeQuestion;
import com.example.bewell.putExtra.PutExtra;
import com.example.bewell.adapters.ExerciseRecViewAdapter;
import com.example.bewell.entryScreens.ExerciseEntry;
import com.example.bewell.models.Exercise;
import com.example.bewell.requestCodes.RequestCode;
import com.example.bewell.requestCodes.ResultCode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class EntryScreenActivity extends AppCompatActivity implements ExerciseRecViewAdapter.OnExerciseListener, FoodRecViewAdapter.OnFoodListener, MoodQuestionsAdapter.MoodListener {

    private RecyclerView exerciseRecView;
    private RecyclerView breakFastRecView;
    private RecyclerView lunchRecView;
    private RecyclerView dinnerRecView;

    private Button addNewFitnessEntryBtn;
    private Button addNewBreakFastBtn;
    private Button addNewLunchBtn;
    private Button addNewDinnerBtn;

    private Context context;
    private ExerciseRecViewAdapter adapter;
    private FoodRecViewAdapter brekfastAdapter;
    private FoodRecViewAdapter lunchAdapter;
    private FoodRecViewAdapter dinnerAdapter;

    private ArrayList<Exercise> userExercises = new ArrayList<>();

    private ArrayList<Food>  breakFastData  =  new ArrayList<>();
    private ArrayList<Food>  lunchData  =  new ArrayList<>();
    private ArrayList<Food>  dinnerData  =  new ArrayList<>();

    private Boolean belongToBreakfast = false;
    private Boolean belongToLunch = false;
    private Boolean belongToDinner = false;




    private RecyclerView moodQuestionRecView;
    private ArrayList<MoodQuestion> moodQuestions =  new ArrayList<>();
    private MoodQuestionsAdapter moodQuestionsAdapter;

    private MoodOfTheDay userMoodOfDay;

    private Button buttonMood100;
    private Button buttonMood80;
    private Button buttonMood60;
    private Button buttonMood40;
    private Button buttonMood20;
    private Button buttonMood0;


    private MaterialTextView statusUserTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_page);

        //DO NOT DELETE CALL AND DEFINITION OF THIS METHOD - USED FOR NAVIGATION BOTTOM
        setUpBottomNavigation();

        //Initialize views
        initializeViews();
        initializeViewsMood();
        initializeButtonMoood();





    }

    private void initializeViewsMood() {

        moodQuestions.add(new MoodQuestion("Do you feeling sad?", TypeQuestion.NEGATIVE));
        moodQuestions.add(new MoodQuestion("Do you feeling happy?", TypeQuestion.POSITIVE));
        moodQuestions.add(new MoodQuestion("Do you feeling tired?", TypeQuestion.NEGATIVE));
        userMoodOfDay = new MoodOfTheDay("today", 0, moodQuestions, 0);
        moodQuestionRecView = findViewById(R.id.moodRecycleView);
        moodQuestionsAdapter = new MoodQuestionsAdapter(this);
        moodQuestionsAdapter.setQuestions(userMoodOfDay.getMoodQuestions());
        moodQuestionRecView.setAdapter(moodQuestionsAdapter);
        moodQuestionRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        statusUserTextView   = findViewById(R.id.moodStatusValue);
        statusUserTextView.setText(userMoodOfDay.getValueStatus() + "%");


    }


    private void initializeButtonMoood(){
        buttonMood100 = findViewById(R.id.button100Value);
        buttonMood80 = findViewById(R.id.button80Value);
        buttonMood60 = findViewById(R.id.button60Value);
        buttonMood40 = findViewById(R.id.button40Value);
        buttonMood20 = findViewById(R.id.button20Value);
        buttonMood0 = findViewById(R.id.button0Value);
        buttonMood100.setOnClickListener(buttonMoodListener);
        buttonMood80.setOnClickListener(buttonMoodListener);
        buttonMood60.setOnClickListener(buttonMoodListener);
        buttonMood40.setOnClickListener(buttonMoodListener);
        buttonMood20.setOnClickListener(buttonMoodListener);
        buttonMood0.setOnClickListener(buttonMoodListener);

    }



    //Functions
    private void initializeViews() {
        exerciseRecView = findViewById(R.id.exerciseRecycleView);
        breakFastRecView = findViewById(R.id.brekfastRecycleView);
        lunchRecView   = findViewById(R.id.lunchRecycleView);
        dinnerRecView  = findViewById(R.id.dinnerRecycleView);


        addNewFitnessEntryBtn = findViewById(R.id.addNewFitnessEntry);
        addNewFitnessEntryBtn.setOnClickListener(buttonOnClickListener);

        addNewBreakFastBtn =  findViewById(R.id.addNewBreakfast);
        addNewBreakFastBtn.setOnClickListener(buttonOnClickListener);


        addNewLunchBtn = findViewById(R.id.addNewLunch);
        addNewLunchBtn.setOnClickListener(buttonOnClickListener);

        addNewDinnerBtn = findViewById(R.id.addNewDinner);
        addNewDinnerBtn.setOnClickListener(buttonOnClickListener);


        context = this;

        //Add mock elements in the list array
        userExercises.add(new Exercise("n", 1, 2, 3, 200));
        userExercises.add(new Exercise("name", 1, 2, 3, 200));
        userExercises.add(new Exercise("tyujj", 1, 2, 3, 200));

        breakFastData.add(new Food("Food1", 23445, 23443, 3243, 466, TypeFood.BREAKFAST));
        breakFastData.add(new Food("Food2", 34, 23443, 3243, 466,TypeFood.BREAKFAST));

        lunchData.add(new Food("lunch1", 54, 66, 78, 99, TypeFood.LUNCH));
        lunchData.add(new Food("lunch2", 334, 67, 789, 909, TypeFood.LUNCH));

        dinnerData.add(new Food("Dinner1", 67, 889, 34, 8, TypeFood.DINNER));
        dinnerData.add(new Food("Dinner2", 455, 12, 334, 8, TypeFood.DINNER));

        //Adapter set up | exercise
        adapter = new ExerciseRecViewAdapter(this);
        adapter.setExercises(userExercises);
        exerciseRecView.setAdapter(adapter);
        exerciseRecView.setLayoutManager(new LinearLayoutManager(this));

        //Adapter set up | breakfast
        brekfastAdapter =  new FoodRecViewAdapter(this,TypeFood.BREAKFAST);
        brekfastAdapter.setFoods(breakFastData);
        breakFastRecView.setAdapter(brekfastAdapter);
        breakFastRecView.setLayoutManager(new LinearLayoutManager(this));



        //Adapter set up | lunch
        lunchAdapter =  new FoodRecViewAdapter(this, TypeFood.LUNCH);
        lunchAdapter.setFoods(lunchData);
        lunchRecView.setAdapter(lunchAdapter);
        lunchRecView.setLayoutManager(new LinearLayoutManager(this));




        //Adapter set up | dinner

        dinnerAdapter =  new FoodRecViewAdapter(this, TypeFood.DINNER);
        dinnerAdapter.setFoods(dinnerData);
        dinnerRecView.setAdapter(dinnerAdapter);
        dinnerRecView.setLayoutManager(new LinearLayoutManager(this));



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

                case R.id.addNewBreakfast:
                    goToCreateFoodForBreakfast(TypeFood.BREAKFAST);
                    break;

                case R.id.addNewLunch:
                    goToCreateFoodForLunch(TypeFood.LUNCH);
                    break;

                case R.id.addNewDinner:
                    goToCreateFoodForDinner(TypeFood.DINNER);
                    break;


                default:
                    break;
            }
        }
    };


    private View.OnClickListener buttonMoodListener   = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int selectedBackground = ContextCompat.getColor(context, R.color.red);
            int unSelectedBackground = ContextCompat.getColor(context, R.color.greyButton);
            switch (v.getId()){
                case R.id.button0Value:

                    buttonMood0.setBackgroundColor(selectedBackground);
                    buttonMood20.setBackgroundColor(unSelectedBackground);
                    buttonMood40.setBackgroundColor(unSelectedBackground);
                    buttonMood60.setBackgroundColor(unSelectedBackground);
                    buttonMood80.setBackgroundColor(unSelectedBackground);
                    buttonMood100.setBackgroundColor(unSelectedBackground);
                    userMoodOfDay.setValueStatus(0);
                    statusUserTextView.setText(userMoodOfDay.getTotalValue() + "%");

                    break;

                case R.id.button20Value:


                    buttonMood0.setBackgroundColor(unSelectedBackground);
                    buttonMood20.setBackgroundColor(selectedBackground);
                    buttonMood40.setBackgroundColor(unSelectedBackground);
                    buttonMood60.setBackgroundColor(unSelectedBackground);
                    buttonMood80.setBackgroundColor(unSelectedBackground);
                    buttonMood100.setBackgroundColor(unSelectedBackground);
                    userMoodOfDay.setValueStatus(20);
                    statusUserTextView.setText(userMoodOfDay.getTotalValue() + "%");
                    break;

                case R.id.button40Value:

                    buttonMood0.setBackgroundColor(unSelectedBackground);
                    buttonMood20.setBackgroundColor(unSelectedBackground);
                    buttonMood40.setBackgroundColor(selectedBackground);
                    buttonMood60.setBackgroundColor(unSelectedBackground);
                    buttonMood80.setBackgroundColor(unSelectedBackground);
                    buttonMood100.setBackgroundColor(unSelectedBackground);
                    userMoodOfDay.setValueStatus(40);
                    statusUserTextView.setText(userMoodOfDay.getTotalValue() + "%");

                    break;

                case R.id.button60Value:

                    buttonMood0.setBackgroundColor(unSelectedBackground);
                    buttonMood20.setBackgroundColor(unSelectedBackground);
                    buttonMood40.setBackgroundColor(unSelectedBackground);
                    buttonMood60.setBackgroundColor(selectedBackground);
                    buttonMood80.setBackgroundColor(unSelectedBackground);
                    buttonMood100.setBackgroundColor(unSelectedBackground);
                    userMoodOfDay.setValueStatus(60);
                    statusUserTextView.setText(userMoodOfDay.getTotalValue() + "%");
                    break;

                case R.id.button80Value:
                    buttonMood0.setBackgroundColor(unSelectedBackground);
                    buttonMood20.setBackgroundColor(unSelectedBackground);
                    buttonMood40.setBackgroundColor(unSelectedBackground);
                    buttonMood60.setBackgroundColor(unSelectedBackground);
                    buttonMood80.setBackgroundColor(selectedBackground);
                    buttonMood100.setBackgroundColor(unSelectedBackground);
                    userMoodOfDay.setValueStatus(80);
                    statusUserTextView.setText(userMoodOfDay.getTotalValue() + "%");
                    break;

                case R.id.button100Value:
                    buttonMood0.setBackgroundColor(unSelectedBackground);
                    buttonMood20.setBackgroundColor(unSelectedBackground);
                    buttonMood40.setBackgroundColor(unSelectedBackground);
                    buttonMood60.setBackgroundColor(unSelectedBackground);
                    buttonMood80.setBackgroundColor(unSelectedBackground);
                    buttonMood100.setBackgroundColor(selectedBackground);
                    userMoodOfDay.setValueStatus(100);
                    statusUserTextView.setText(userMoodOfDay.getTotalValue() + "%");
                    break;

            }
        }
    };


//    private void setFocus(Button btn_unfocus, Button btn_focus){
//        btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
//        btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
//        btn_focus.setTextColor(Color.rgb(255, 255, 255));
//        btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
//        this.btn_unfocus = btn_focus;
//    }

    //Add new food for breakfast
    private void goToCreateFoodForBreakfast(TypeFood typeFood){
        Intent addFoodScreen  =  new Intent(context, AddFood.class);
        addFoodScreen.putExtra(PutExtra.TYPE_FOOD_ADDED, typeFood);
        startActivityForResult(addFoodScreen, RequestCode.CREATE_NEW_FOOD_BREAKFAST);
    }

    //Add new food for lunch
    private void goToCreateFoodForLunch(TypeFood typeFood){
        Intent addFoodScreen  =  new Intent(context, AddFood.class);
        addFoodScreen.putExtra(PutExtra.TYPE_FOOD_ADDED, typeFood);
        startActivityForResult(addFoodScreen, RequestCode.CREATE_NEW_FOOD_LUNCH);
    }

    //Add new food for sinner
    private void goToCreateFoodForDinner(TypeFood typeFood){
        Intent addFoodScreen  =  new Intent(context, AddFood.class);
        addFoodScreen.putExtra(PutExtra.TYPE_FOOD_ADDED, typeFood);
        startActivityForResult(addFoodScreen, RequestCode.CREATE_NEW_FOOD_DINNER);
    }




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
            if (resultCode == ResultCode.UPDATE_FITNESS_RECORD) {
                //Exist from the code if position is not returned correctly
                if (positionToChangeOnArray == -1) {
                    return;
                } else {
                    updateExistingFitnessEntry(data, positionToChangeOnArray);
                }
            } else if (resultCode == ResultCode.DELETE_FITNESS_RECORD) {
                if (positionToChangeOnArray == -1) {
                    return;
                } else {
                    deleteFitnessEntry(positionToChangeOnArray);
                }
            }
        } else if (requestCode ==  RequestCode.CREATE_NEW_FOOD_BREAKFAST){

            if (resultCode == ResultCode.CREATE_NEW_FOOD_BREKFAST){
                createNewFoodForBreafast(data);
            }else {
                Toast.makeText(EntryScreenActivity.this, "sommething wrong brekfast", Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode ==  RequestCode.CREATE_NEW_FOOD_LUNCH){
            if (resultCode == ResultCode.CREATE_NEW_FOOD_LUNCH){
                createNewFoodForLunch(data);
            }else {
                Toast.makeText(EntryScreenActivity.this, "sommething wrong with lunch ", Toast.LENGTH_LONG).show();
            }

        }

        else if (requestCode ==  RequestCode.CREATE_NEW_FOOD_DINNER){
            if (resultCode == ResultCode.CREATE_NEW_FOOD_DINNER){
                createNewFoodForDinner(data);
            }else {
                Toast.makeText(EntryScreenActivity.this, "sommething wrong with dinner ", Toast.LENGTH_LONG).show();
            }

        }

        else if (requestCode == RequestCode.EDIT_EXISTING_FOOD){
            int postionFood = data.getIntExtra(PutExtra.POSITION_OF_FOOD, -1);
            if (resultCode == ResultCode.UPDATE_FOOD){
                if (postionFood == -1){
                    return;
                }else {
                    updateExistingFoodEntry(data, postionFood);
                }
            }else if (resultCode == ResultCode.DELETE_FOOD){

                if (postionFood == -1) {

                    return;
                } else {

                    deleteExistingFoodEntry(data, postionFood);
                    return;

                }

            }
        }
    }


    //Create new food for breakfast + append to the array
    private void createNewFoodForBreafast(Intent data) {
        Food  foodToAdd = data.getParcelableExtra(PutExtra.NEW_FOOD_BREKFAST);
        breakFastData.add(foodToAdd);
        brekfastAdapter.setFoods(breakFastData);
    }


    private void updateExistingFoodEntry (Intent data, int positionFood){

        Food foodToEdit  = data.getParcelableExtra(PutExtra.UPDATE_EXISTING_FOOD);
        TypeFood typeFoodFrom =  (TypeFood) foodToEdit.getTypeFood();

        switch (typeFoodFrom){
            case BREAKFAST:
                updateBreakFastData(foodToEdit, positionFood);
                break;
            case LUNCH:
                updateLunchData(foodToEdit, positionFood);
                break;
            case DINNER:
                updateDinnerData(foodToEdit, positionFood);
                break;
        }



    }


    private void deleteExistingFoodEntry (Intent data, int positionFood){

        TypeFood typeFoodToDelete  = (TypeFood ) data.getSerializableExtra(PutExtra.DELETE_TYPE_FOOD);

        switch (typeFoodToDelete){
            case BREAKFAST:
                deleteBreakFastData(positionFood);
                break;
            case LUNCH:
                deleteLunchData(positionFood);
                break;
            case DINNER:
                deleteDinnerData(positionFood);
                break;
        }



    }

    private void deleteBreakFastData(int positionInBreakfastDel){
        breakFastData.remove(positionInBreakfastDel);
        brekfastAdapter.setFoods(breakFastData);
    }
    private void deleteLunchData(int positionInLunchDel){
        lunchData.remove(positionInLunchDel);
        lunchAdapter.setFoods(lunchData);
    }
    private void deleteDinnerData(int positionInDinnertDel){
        dinnerData.remove(positionInDinnertDel);
        dinnerAdapter.setFoods(dinnerData);
    }




    private void updateBreakFastData(Food foodToUpdate, int positionInBreakfast){
        breakFastData.set(positionInBreakfast,foodToUpdate);
        brekfastAdapter.setFoods(breakFastData);
    }

    private void updateLunchData(Food foodToUpdate, int positionInLunch){
        lunchData.set(positionInLunch,foodToUpdate);
        lunchAdapter.setFoods(lunchData);
    }
    private void updateDinnerData(Food foodToUpdate, int positionInDinner){
        dinnerData.set(positionInDinner,foodToUpdate);
        dinnerAdapter.setFoods(dinnerData);
    }


    //Create new food for lunch + append to the array
    private void createNewFoodForLunch(Intent data) {
        Food  foodToAdd = data.getParcelableExtra(PutExtra.NEW_FOOD_LUNCH);
        lunchData.add(foodToAdd);
        lunchAdapter.setFoods(lunchData);
    }


    //Create new food for dinner + append to the array
    private void createNewFoodForDinner(Intent data) {
        Food  foodToAdd = data.getParcelableExtra(PutExtra.NEW_FOOD_DINNER);
        dinnerData.add(foodToAdd);
        dinnerAdapter.setFoods(dinnerData);
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


    @Override
    public void onFoodClick(int position, TypeFood type) {
        switch (type){
            case BREAKFAST:
                breakfastFoodClicked(position);
                break;
            case LUNCH:
                lunchFoodClicked(position);
                break;
            case DINNER:
                dinnerFoodClicked(position);
                break;
        }
    }

    private void breakfastFoodClicked (int position){
        Toast.makeText(EntryScreenActivity.this, "" + breakFastData.get(position), Toast.LENGTH_LONG);
        Intent modifyFoodScreen = new Intent(EntryScreenActivity.this, AddFood.class);
        modifyFoodScreen.putExtra(PutExtra.EXISTING_FOOD, breakFastData.get(position));
        modifyFoodScreen.putExtra(PutExtra.POSITION_OF_FOOD, position);
        startActivityForResult(modifyFoodScreen, RequestCode.EDIT_EXISTING_FOOD);
    }


    private void lunchFoodClicked (int position){
        Toast.makeText(EntryScreenActivity.this, "" + lunchData.get(position), Toast.LENGTH_LONG);
        Intent modifyFoodScreen = new Intent(EntryScreenActivity.this, AddFood.class);
        modifyFoodScreen.putExtra(PutExtra.EXISTING_FOOD, lunchData.get(position));
        modifyFoodScreen.putExtra(PutExtra.POSITION_OF_FOOD, position);
        startActivityForResult(modifyFoodScreen, RequestCode.EDIT_EXISTING_FOOD);
    }

    private void dinnerFoodClicked (int position){
        Toast.makeText(EntryScreenActivity.this, "" + dinnerData.get(position), Toast.LENGTH_LONG);
        Intent modifyFoodScreen = new Intent(EntryScreenActivity.this, AddFood.class);
        modifyFoodScreen.putExtra(PutExtra.EXISTING_FOOD, dinnerData.get(position));
        modifyFoodScreen.putExtra(PutExtra.POSITION_OF_FOOD, position);
        startActivityForResult(modifyFoodScreen, RequestCode.EDIT_EXISTING_FOOD);
    }

    @Override
    public void positiviteMoodOnClickButton(int position) {
            userMoodOfDay.getMoodQuestions().get(position).setSetQuestionAnswer(true);
            MoodQuestion userAnswer = userMoodOfDay.getMoodQuestions().get(position);
            if (userAnswer.isAnswer() == false) {
                userMoodOfDay.incrementAdditionalValue(8);
                ArrayList<MoodQuestion> newQuestions = userMoodOfDay.getMoodQuestions();
                newQuestions.get(position).setAnswer(true);
                userMoodOfDay.setMoodQuestions(newQuestions);
                System.out.println(userAnswer);
                statusUserTextView.setText(userMoodOfDay.getTotalValue() + "%");
                moodQuestionsAdapter.setQuestions(userMoodOfDay.getMoodQuestions());
            }

            return;
    }


    @Override
    public void negativeMoodOnClickButton(int position) {


        userMoodOfDay.getMoodQuestions().get(position).setSetQuestionAnswer(true);
        MoodQuestion userAnswer = userMoodOfDay.getMoodQuestions().get(position);

        if (userAnswer.isAnswer() == true){
            userMoodOfDay.incrementAdditionalValue(-8);
            userMoodOfDay.getMoodQuestions().get(position).setAnswer(false);
            statusUserTextView.setText(userMoodOfDay.getTotalValue() + "%");
            moodQuestionsAdapter.setQuestions(userMoodOfDay.getMoodQuestions());
        }
        return;

    }
}