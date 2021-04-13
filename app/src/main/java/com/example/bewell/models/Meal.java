package com.example.bewell.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Meal implements Parcelable {
    private ArrayList<Food> foods;
    private int totalCaloriesMeals;


    public Meal(ArrayList<Food> foods, int totalCaloriesMeals) {
        this.foods = foods;
        this.totalCaloriesMeals = totalCaloriesMeals;
    }

    public Meal() {
        this.foods = null;
        totalCaloriesMeals = 0;
    }




    protected Meal(Parcel in) {
        foods = in.createTypedArrayList(Food.CREATOR);
        totalCaloriesMeals = in.readInt();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(foods);
        dest.writeInt(totalCaloriesMeals);
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public int getTotalCaloriesMeals() {
        return totalCaloriesMeals;
    }

    public void setTotalCaloriesMeals(int totalCaloriesMeals) {
        this.totalCaloriesMeals = totalCaloriesMeals;
    }

    public static Creator<Meal> getCREATOR() {
        return CREATOR;
    }


}
