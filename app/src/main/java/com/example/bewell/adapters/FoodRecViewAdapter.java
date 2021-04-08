package com.example.bewell.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bewell.R;
import com.example.bewell.models.Food;
import com.example.bewell.models.TypeFood;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class FoodRecViewAdapter extends RecyclerView.Adapter<FoodRecViewAdapter.ViewHolder> {
    private ArrayList<Food> foods = new ArrayList<>();

    private  OnFoodListener onFoodListener;
    private  TypeFood typeFood;


    public FoodRecViewAdapter(OnFoodListener onFoodListener, TypeFood type) {
        this.onFoodListener =  onFoodListener;
        this.typeFood = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_individual_food, parent, false);
        ViewHolder holder = new  ViewHolder(view, onFoodListener,typeFood);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food foodAtRow  =  foods.get(position);
        holder.nameFood.setText(foodAtRow.getName());
        String caloriesInt  = foodAtRow.getCalories() +  " kcal";
        holder.caloriesGainedFood.setText(caloriesInt);

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private MaterialTextView nameFood;
        private  MaterialTextView caloriesGainedFood;
        private OnFoodListener onFoodListener;
        private TypeFood typeFood;

        public ViewHolder(@NonNull View itemView, OnFoodListener  onFoodListerR, TypeFood type) {
            super(itemView);
            nameFood =  itemView.findViewById(R.id.labelForNameFood);
            caloriesGainedFood =  itemView.findViewById(R.id.labelForCaloriesGainedFood);
            this.typeFood = type;
            this.onFoodListener = onFoodListerR;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFoodListener.onFoodClick(getAdapterPosition(), typeFood);

        }
    }


    public interface  OnFoodListener{
        void onFoodClick(int position, TypeFood type);
    }
}
