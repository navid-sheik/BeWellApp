package com.example.bewell.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bewell.models.Exercise;
import com.example.bewell.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ExerciseRecViewAdapter extends RecyclerView.Adapter<ExerciseRecViewAdapter.ViewHolder> {
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private OnExerciseListener onExerciseListener;

    public ExerciseRecViewAdapter() {
    }

    public ExerciseRecViewAdapter(OnExerciseListener onExerciseListener) {
        this.onExerciseListener = onExerciseListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_invidual_exercise, parent, false);
        ViewHolder holder = new ViewHolder(view, onExerciseListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exerciseAtRow  = exercises.get(position);
        holder.nameExercise.setText(exerciseAtRow.getName());
        String setPlusRepString  = exerciseAtRow.getSets() + "x"  + exerciseAtRow.getRepetitions();
        holder.setAndRep.setText(setPlusRepString );
        String minutesString  =  exerciseAtRow.getDuration() + " min";
        holder.durationExercise.setText(minutesString);
        String caloriesString  =  exerciseAtRow.getCaloriesBurned() + " kcal";
        holder.caloriesBurned.setText(caloriesString);

    }

    @Override
    public int getItemCount() {
        return exercises.size();

    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialTextView nameExercise;
        private MaterialTextView setAndRep;
        private MaterialTextView durationExercise;
        private MaterialTextView caloriesBurned;

        OnExerciseListener onExerciseListener;

        public ViewHolder(@NonNull View itemView, OnExerciseListener onExerciseListenerR) {
            super(itemView);
            nameExercise = itemView.findViewById(R.id.labelForNameExercise);
            setAndRep = itemView.findViewById(R.id.labelForRepetition);
            durationExercise = itemView.findViewById(R.id.labelForDuration);
            caloriesBurned = itemView.findViewById(R.id.labelForCaloriesBurned);
            this.onExerciseListener = onExerciseListenerR;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.v("Something", "It clicked");
            onExerciseListener.onExerciseClick(getAdapterPosition());

        }
    }

    public interface OnExerciseListener {
        void onExerciseClick(int position);
    }

}
