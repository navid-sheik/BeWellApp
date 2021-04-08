package com.example.bewell.entryScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.example.bewell.R;
import com.example.bewell.models.Food;
import com.example.bewell.models.TypeFood;
import com.example.bewell.putExtra.PutExtra;
import com.example.bewell.requestCodes.ResultCode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddFood extends AppCompatActivity {

    private Button confirmFoodButton ;
    private  Button cancelFoodButton;


    private TextInputEditText nameFoodTxtField;
    private TextInputLayout nameFoodLayoutField;


    private TextInputEditText caloriesFoodTxtField;
    private TextInputLayout caloriesFoodLayoutField;

    private TextInputEditText proteinFoodTxtField;
    private TextInputLayout proteinFoodLayoutField;


    private TextInputEditText carbsFoodTxtField;
    private TextInputLayout carbssFoodLayoutField;

    private TextInputEditText fatsFoodTxtField;
    private TextInputLayout fatsFoodLayoutField;
    private TypeFood typeFood;

    private boolean isNewFood = true;
    private int positionOfExisting   =  -1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        initiliazeViews();
        validationTextField();

        Intent fromEntryScreen  =  getIntent();
        if(fromEntryScreen.hasExtra(PutExtra.TYPE_FOOD_ADDED)){
            typeFood = (TypeFood) fromEntryScreen.getSerializableExtra(PutExtra.TYPE_FOOD_ADDED);
        }
        if (fromEntryScreen.hasExtra(PutExtra.EXISTING_FOOD) && fromEntryScreen.hasExtra(PutExtra.POSITION_OF_FOOD)){
            Food existingFood  = fromEntryScreen.getParcelableExtra(PutExtra.EXISTING_FOOD);
            positionOfExisting  = fromEntryScreen.getIntExtra(PutExtra.POSITION_OF_FOOD, -1);
            isNewFood = false;
            typeFood = existingFood.getTypeFood();
            confirmFoodButton.setText("Save");
            cancelFoodButton.setText("Delete");
            nameFoodTxtField.setText(String.valueOf(existingFood.getName()));
            caloriesFoodTxtField.setText(String.valueOf(existingFood.getCalories()));
            proteinFoodTxtField.setText(String.valueOf(existingFood.getProtein()));
            carbsFoodTxtField.setText(String.valueOf(existingFood.getCarbs()));
            fatsFoodTxtField.setText(String.valueOf(existingFood.getFats()));
        }

    }

    private void initiliazeViews(){
        confirmFoodButton   =  findViewById(R.id.confirmFoodEntry);
        confirmFoodButton.setOnClickListener(foodEntryClickListener);

        cancelFoodButton =  findViewById( R.id.cancelFoodEntry);
        cancelFoodButton.setOnClickListener(foodEntryClickListener);
        nameFoodLayoutField =  findViewById(R.id.foodNameInputLayout);

        nameFoodTxtField  =  findViewById(R.id.nameFoodTxtField);
        caloriesFoodLayoutField  =  findViewById(R.id.foodCaloriesInputLayout);
        caloriesFoodTxtField  =  findViewById(R.id.foodCaloriesInputTxtFiled);


        proteinFoodLayoutField = findViewById(R.id.foodProteinInputLayout);
        proteinFoodTxtField  = findViewById(R.id.foodProteinInputTxtField);

        carbsFoodTxtField =  findViewById(R.id.foodCarbInputTxtField);
        carbssFoodLayoutField = findViewById(R.id.foodCarbInputLayout);

        fatsFoodLayoutField =  findViewById(R.id.foodFatsInputLayout);
        fatsFoodTxtField = findViewById(R.id.foodFatsInputTxtField);



    }


    private void validationTextField (){
        //Text Updates for fields
        nameFoodTxtField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nameFoodLayoutField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strWorkoutName = nameFoodTxtField.getText().toString();
                if (strWorkoutName.isEmpty()) {
                    nameFoodLayoutField.setError("Field can be empty");
                    confirmFoodButton.setEnabled(false);
                    confirmFoodButton.setTextColor(Color.RED);
                    confirmFoodButton.getBackground().setAlpha(128);


                } else {
                    nameFoodLayoutField.setError(null);
                    confirmFoodButton.setTextColor(Color.BLACK);
                    confirmFoodButton.getBackground().setAlpha(255);
                    confirmFoodButton.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        caloriesFoodTxtField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                caloriesFoodLayoutField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strWorkoutName = caloriesFoodTxtField.getText().toString();
                if (strWorkoutName.isEmpty()) {
                    caloriesFoodLayoutField.setError("Field can be empty");
                    confirmFoodButton.setEnabled(false);
                    confirmFoodButton.setTextColor(Color.RED);
                    confirmFoodButton.getBackground().setAlpha(128);


                } else {
                    caloriesFoodLayoutField.setError(null);
                    confirmFoodButton.setTextColor(Color.BLACK);
                    confirmFoodButton.getBackground().setAlpha(255);
                    confirmFoodButton.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        proteinFoodTxtField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                proteinFoodLayoutField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strWorkoutName = proteinFoodTxtField.getText().toString();
                if (strWorkoutName.isEmpty()) {
                    proteinFoodLayoutField.setError("Field can be empty");
                    confirmFoodButton.setEnabled(false);
                    confirmFoodButton.setTextColor(Color.RED);
                    confirmFoodButton.getBackground().setAlpha(128);


                } else {
                    proteinFoodLayoutField.setError(null);
                    confirmFoodButton.setTextColor(Color.BLACK);
                    confirmFoodButton.getBackground().setAlpha(255);
                    confirmFoodButton.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        carbsFoodTxtField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                carbssFoodLayoutField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strWorkoutName = carbsFoodTxtField.getText().toString();
                if (strWorkoutName.isEmpty()) {
                    carbssFoodLayoutField.setError("Field can be empty");
                    confirmFoodButton.setEnabled(false);
                    confirmFoodButton.setTextColor(Color.RED);
                    confirmFoodButton.getBackground().setAlpha(128);


                } else {
                    carbssFoodLayoutField.setError(null);
                    confirmFoodButton.setTextColor(Color.BLACK);
                    confirmFoodButton.getBackground().setAlpha(255);
                    confirmFoodButton.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        fatsFoodTxtField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                fatsFoodLayoutField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strWorkoutName = fatsFoodTxtField.getText().toString();
                if (strWorkoutName.isEmpty()) {
                    fatsFoodLayoutField.setError("Field can be empty");
                    confirmFoodButton.setEnabled(false);
                    confirmFoodButton.setTextColor(Color.RED);
                    confirmFoodButton.getBackground().setAlpha(128);


                } else {
                    fatsFoodLayoutField.setError(null);
                    confirmFoodButton.setTextColor(Color.BLACK);
                    confirmFoodButton.getBackground().setAlpha(255);
                    confirmFoodButton.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }



    private View.OnClickListener foodEntryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case (R.id.confirmFoodEntry):
                    clickSaveOrUpdateFood();
                case  (R.id.cancelFoodEntry):
                    clickCancelOrDeleteFood();
                default:
                    break;


            }
        }

    };




    private void clickSaveOrUpdateFood (){

        String strFoodName = nameFoodTxtField.getText().toString();
        String strCaloriesFood = caloriesFoodTxtField.getText().toString();
        String strCProteinsFood = proteinFoodTxtField.getText().toString();
        String strCabsFood = carbsFoodTxtField.getText().toString();
        String strFatsFood = fatsFoodTxtField.getText().toString();


        if (!strFoodName.isEmpty() && !strCaloriesFood.isEmpty() && !strCProteinsFood.isEmpty()  && !strCabsFood.isEmpty() && !strFatsFood.isEmpty()){
            int intCaloriesFood = convertInt(strCaloriesFood);
            int intProteinFood = convertInt(strCProteinsFood);

            int intCarbsFood = convertInt(strCabsFood);
            int intFatsFood = convertInt(strFatsFood);



            Food newFood =  new Food(strFoodName, intCaloriesFood, intProteinFood, intCarbsFood, intFatsFood, typeFood);

            confirmFoodButton.setTextColor(Color.BLACK);
            confirmFoodButton.getBackground().setAlpha(255);
            confirmFoodButton.setEnabled(true);

            if (isNewFood && positionOfExisting == -1){
                createFoodRecord(newFood);
            }else {
                int copyPosition = positionOfExisting;
                positionOfExisting =  -1;
                isNewFood = true;
                updateFoodRecord(newFood, copyPosition);
            }





        }




    }

    private void createFoodRecord  (Food foodToAdd){

        if (typeFood != null){
            switch (typeFood){
                case BREAKFAST:
                    breakFastFoodRecord(foodToAdd);
                    break;
                case LUNCH:
                    lunchFoodRecord(foodToAdd);

                    break;
                case DINNER:

                    dinnerFoodRecord(foodToAdd);
                    break;

            }
        }

    }


    private void breakFastFoodRecord(Food breakfastFoodAdd){
        Intent  entrActivity  = new Intent();
        entrActivity.putExtra(PutExtra.NEW_FOOD_BREKFAST, breakfastFoodAdd);
        setResult(ResultCode.CREATE_NEW_FOOD_BREKFAST, entrActivity);
        finish();


    }

    private void lunchFoodRecord(Food lunchFoodAdd){
        Intent  entrActivity  = new Intent();
        entrActivity.putExtra(PutExtra.NEW_FOOD_LUNCH, lunchFoodAdd);
        setResult(ResultCode.CREATE_NEW_FOOD_LUNCH, entrActivity);
        finish();


    }

    private void dinnerFoodRecord(Food dinnerFoodToAdd){
        Intent  entrActivity  = new Intent();
        entrActivity.putExtra(PutExtra.NEW_FOOD_DINNER, dinnerFoodToAdd);
        setResult(ResultCode.CREATE_NEW_FOOD_DINNER, entrActivity);
        finish();


    }





    private void updateFoodRecord (Food foodToUpdate, int positionToChange){
        Intent  backToMainActivity  = new Intent();
        backToMainActivity.putExtra(PutExtra.UPDATE_EXISTING_FOOD, foodToUpdate);
        backToMainActivity.putExtra(PutExtra.POSITION_OF_FOOD, positionToChange);
        setResult(ResultCode.UPDATE_FOOD, backToMainActivity);
        finish();


    }

    private void clickCancelOrDeleteFood(){
        if (isNewFood && positionOfExisting == -1){
            finish();
        }else {
            int copyPosition  =  positionOfExisting;
            positionOfExisting = -1;
            isNewFood = true;
            deleteFoodRecord(copyPosition, typeFood);
        }

    }

    private void deleteFoodRecord(int positionOfExisting, TypeFood typeArrayToDeleteFrom){
        Intent backToEntryScreen =  new Intent();
        backToEntryScreen.putExtra(PutExtra.DELETE_TYPE_FOOD,  typeArrayToDeleteFrom);
        backToEntryScreen.putExtra(PutExtra.POSITION_OF_FOOD,  positionOfExisting);
        setResult(ResultCode.DELETE_FOOD, backToEntryScreen);
        finish();
    }



    //Helper function
    public boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public int convertInt(String s) {
        int value = Integer.parseInt(s);
        return value;
    }



}