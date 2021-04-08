package com.example.bewell.entryScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.example.bewell.putExtra.PutExtra;
import com.example.bewell.R;
import com.example.bewell.models.Exercise;
import com.example.bewell.requestCodes.ResultCode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ExerciseEntry extends AppCompatActivity {
    //TODO : errror top prevent empty entry is submitted
    //TODO: implement total of the day
    private Button confirmEntryBtn;
    private Button cancelEntryBtn;
    private Boolean newRecord = true;
    private int positionOfExisting = -1;

    private TextInputEditText nameWorkoutTxtField;
    private TextInputLayout nameWorkoutLayoutField;

    private TextInputEditText setWorkoutTxtField;
    private TextInputLayout setWorkoutLayoutField;

    private TextInputEditText repWorkoutTxtField;
    private TextInputLayout repWorkoutLayoutField;

    private TextInputEditText durationWorkoutTxtField;
    private TextInputLayout durationWorkoutLayoutField;

    private TextInputEditText caloriesBurnedWorkoutTxtField;
    private TextInputLayout caloriesBurnedWorkoutLayoutField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        initialiseView();

        //Fill the fields with the value passed in the extra and change the functions
        Intent intent = getIntent();
        if (intent.hasExtra(PutExtra.EXISTING_EXERCISE) && intent.hasExtra(PutExtra.POSITION_OF_FITNESS_ENTRY)) {
            Exercise existingExercise = intent.getParcelableExtra(PutExtra.EXISTING_EXERCISE);
            confirmEntryBtn.setText("Save");
            cancelEntryBtn.setText("Delete");
            nameWorkoutTxtField.setText(String.valueOf(existingExercise.getName()));
            setWorkoutTxtField.setText(String.valueOf(existingExercise.getSets()));
            repWorkoutTxtField.setText(String.valueOf(existingExercise.getRepetitions()));
            durationWorkoutTxtField.setText(String.valueOf(existingExercise.getDuration()));
            caloriesBurnedWorkoutTxtField.setText(String.valueOf( existingExercise.getCaloriesBurned()));
            newRecord = false;
            positionOfExisting = intent.getIntExtra(PutExtra.POSITION_OF_FITNESS_ENTRY, -1);
        }

        //Text Updates for fields
        nameWorkoutTxtField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nameWorkoutLayoutField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strWorkoutName = nameWorkoutTxtField.getText().toString();
                if (strWorkoutName.isEmpty()) {
                    nameWorkoutLayoutField.setError("Field can be empty");
                    confirmEntryBtn.setEnabled(false);
                    confirmEntryBtn.setTextColor(Color.RED);
                    confirmEntryBtn.getBackground().setAlpha(128);


                } else {
                    nameWorkoutLayoutField.setError(null);
                    confirmEntryBtn.setTextColor(Color.BLACK);
                    confirmEntryBtn.getBackground().setAlpha(255);
                    confirmEntryBtn.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        nameWorkoutTxtField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    nameWorkoutLayoutField.setError(null);
                }
            }
        });

        //Text Updates for fields
        setWorkoutTxtField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setWorkoutTxtField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String strWorkoutSets = setWorkoutTxtField.getText().toString();
                //int convertedValueSets  =  Integer.parseInt(strWorkoutSets);
                if (!strWorkoutSets.isEmpty()) {
                    if (isStringInt(strWorkoutSets)) {
                        setWorkoutLayoutField.setError(null);
                        confirmEntryBtn.setTextColor(Color.BLACK);
                        confirmEntryBtn.getBackground().setAlpha(255);
                        confirmEntryBtn.setEnabled(true);

                    } else {
                        setWorkoutLayoutField.setError("Please an integer");
                        confirmEntryBtn.setEnabled(false);
                        confirmEntryBtn.setTextColor(Color.RED);
                        confirmEntryBtn.getBackground().setAlpha(128);
                    }


                } else {

                    setWorkoutLayoutField.setError("Field can be empty");
                    confirmEntryBtn.setEnabled(false);
                    confirmEntryBtn.setTextColor(Color.RED);
                    confirmEntryBtn.getBackground().setAlpha(128);

                }


            }
        });

        //Text Updates for fields
        caloriesBurnedWorkoutTxtField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                caloriesBurnedWorkoutLayoutField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strWorkoutCalories = caloriesBurnedWorkoutTxtField.getText().toString();
                //int convertedValueSets  =  Integer.parseInt(strWorkoutSets);
                if (!strWorkoutCalories.isEmpty()) {
                    if (isStringInt(strWorkoutCalories)) {
                        caloriesBurnedWorkoutLayoutField.setError(null);
                        confirmEntryBtn.setTextColor(Color.BLACK);
                        confirmEntryBtn.getBackground().setAlpha(255);
                        confirmEntryBtn.setEnabled(true);

                    } else {
                        caloriesBurnedWorkoutLayoutField.setError("Please an integer");
                        confirmEntryBtn.setEnabled(false);
                        confirmEntryBtn.setTextColor(Color.RED);
                        confirmEntryBtn.getBackground().setAlpha(128);

                    }
                } else {

                    caloriesBurnedWorkoutLayoutField.setError("Field can be empty");
                    confirmEntryBtn.setEnabled(false);
                    confirmEntryBtn.setTextColor(Color.RED);
                    confirmEntryBtn.getBackground().setAlpha(128);

                }


            }
        });

        //Text Updates for fields
        durationWorkoutTxtField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                durationWorkoutLayoutField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strWorkoutDuration = durationWorkoutTxtField.getText().toString();
                //int convertedValueSets  =  Integer.parseInt(strWorkoutSets);
                if (!strWorkoutDuration.isEmpty()) {
                    if (isStringInt(strWorkoutDuration)) {
                        durationWorkoutLayoutField.setError(null);
                        confirmEntryBtn.setTextColor(Color.BLACK);
                        confirmEntryBtn.getBackground().setAlpha(255);
                        confirmEntryBtn.setEnabled(true);

                    } else {
                        durationWorkoutLayoutField.setError("Please an integer");
                        confirmEntryBtn.setEnabled(false);
                        confirmEntryBtn.setTextColor(Color.RED);
                        confirmEntryBtn.getBackground().setAlpha(128);

                    }


                } else {

                    durationWorkoutLayoutField.setError("Field can be empty");
                    confirmEntryBtn.setEnabled(false);
                    confirmEntryBtn.setTextColor(Color.RED);
                    confirmEntryBtn.getBackground().setAlpha(128);

                }


            }
        });

        //Text Updates for fields
        repWorkoutTxtField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                repWorkoutLayoutField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strWorkoutRep = repWorkoutTxtField.getText().toString();
                //int convertedValueSets  =  Integer.parseInt(strWorkoutSets);
                if (!strWorkoutRep.isEmpty()) {
                    if (isStringInt(strWorkoutRep)) {
                        repWorkoutLayoutField.setError(null);
                        confirmEntryBtn.setTextColor(Color.BLACK);
                        confirmEntryBtn.getBackground().setAlpha(255);
                        confirmEntryBtn.setEnabled(true);

                    } else {
                        repWorkoutLayoutField.setError("Please an integer");
                        confirmEntryBtn.setEnabled(false);
                        confirmEntryBtn.setTextColor(Color.RED);
                        confirmEntryBtn.getBackground().setAlpha(128);

                    }


                } else {

                    repWorkoutLayoutField.setError("Field can be empty");
                    confirmEntryBtn.setEnabled(false);
                    confirmEntryBtn.setTextColor(Color.RED);
                    confirmEntryBtn.getBackground().setAlpha(128);

                }


            }
        });


    }

    private View.OnClickListener entryScreenButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirmFitnessEntry:
                    clickSaveOrUpdate();
                case R.id.cancelFitnessEntry:
                    clickCancelOrDelete();
                default:
                    return;
            }

        }
    };


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

    private void initialiseView() {
        confirmEntryBtn = findViewById(R.id.confirmFitnessEntry);
        confirmEntryBtn.setOnClickListener(entryScreenButtonListener);
        confirmEntryBtn.setEnabled(false);
        cancelEntryBtn = findViewById(R.id.cancelFitnessEntry);
        cancelEntryBtn.setOnClickListener(entryScreenButtonListener);
        nameWorkoutTxtField = findViewById(R.id.nameWorkoutTxtField);
        nameWorkoutLayoutField = findViewById(R.id.workoutNameInputLayout);

        setWorkoutTxtField = findViewById(R.id.setWorkoutTxtField);
        setWorkoutLayoutField = findViewById(R.id.workoutSetInputLayout);

        repWorkoutTxtField = findViewById(R.id.repWorkoutTxtField);
        repWorkoutLayoutField = findViewById(R.id.workoutRepInputLayout);

        durationWorkoutTxtField = findViewById(R.id.durationWorkoutTxtField);
        durationWorkoutLayoutField = findViewById(R.id.workoutDurationInputLayout);


        caloriesBurnedWorkoutTxtField = findViewById(R.id.caloriesBurnedWorkoutTxtField);
        caloriesBurnedWorkoutLayoutField = findViewById(R.id.workoutCaloriesBurnedInputLayout);

    }

    private void clickSaveOrUpdate() {
        String strWorkoutName = nameWorkoutTxtField.getText().toString();
        String strSetWorkout = setWorkoutTxtField.getText().toString();
        String strRepWorkout = repWorkoutTxtField.getText().toString();
        String strDurationWorkout = durationWorkoutTxtField.getText().toString();
        String strCaloriesBurnedWorkout = caloriesBurnedWorkoutTxtField.getText().toString();


        if (!strWorkoutName.isEmpty() && !strSetWorkout.isEmpty() && !strRepWorkout.isEmpty() && !strDurationWorkout.isEmpty() && !strCaloriesBurnedWorkout.isEmpty()) {
            int intSetWorkout = convertInt(setWorkoutTxtField.getText().toString());
            int intRepWorkout = convertInt(repWorkoutTxtField.getText().toString());
            int intDurationWorkout = convertInt(durationWorkoutTxtField.getText().toString());
            int intCaloriesBurnedWorkout = convertInt(caloriesBurnedWorkoutTxtField.getText().toString());


            confirmEntryBtn.setTextColor(Color.BLACK);
            confirmEntryBtn.getBackground().setAlpha(255);
            confirmEntryBtn.setEnabled(true);
            nameWorkoutLayoutField.setError(null);
            Exercise theExercise = new Exercise(strWorkoutName, intSetWorkout, intRepWorkout, intDurationWorkout, intCaloriesBurnedWorkout);
            if (newRecord && positionOfExisting == -1) {
                createNewRecord(theExercise);
            } else {
                int copyPosition = positionOfExisting;
                positionOfExisting = -1;
                newRecord = false;
                updateRecord(theExercise, copyPosition);
            }
        }
        return;


    }


    private void clickCancelOrDelete() {
        String strWorkoutName = nameWorkoutTxtField.getText().toString();
        nameWorkoutLayoutField.setError(null);
        Exercise theExercise = new Exercise(strWorkoutName, 1, 2, 23, 2237);
        if (newRecord && positionOfExisting == -1) {
            returnToMain();
        } else {
            int copyPosition = positionOfExisting;
            positionOfExisting = -1;
            newRecord = false;
            deleteExistingRecord(theExercise, copyPosition);
        }


    }

    public void createNewRecord(Exercise exerciseToAdd) {
        Intent mainActivity = new Intent();
        mainActivity.putExtra(PutExtra.NEW_EXERCISE, exerciseToAdd);
        setResult(RESULT_OK, mainActivity);
        finish();
    }

    public void updateRecord(Exercise exerciseToModify, int position) {
        Intent mainActivity = new Intent();
        mainActivity.putExtra(PutExtra.UPDATE_EXCERCISE, exerciseToModify);
        mainActivity.putExtra(PutExtra.POSITION_OF_FITNESS_ENTRY, position);
        setResult(ResultCode.UPDATE_FITNESS_RECORD, mainActivity);
        finish();
    }

    public void returnToMain() {
        finish();
    }

    public void deleteExistingRecord(Exercise exerciseToDelete, int position) {
        Intent mainActivity = new Intent();
        mainActivity.putExtra(PutExtra.DELETE_EXERCISE, exerciseToDelete);
        mainActivity.putExtra(PutExtra.POSITION_OF_FITNESS_ENTRY, position);
        setResult(ResultCode.DELETE_FITNESS_RECORD, mainActivity);
        finish();


    }

}