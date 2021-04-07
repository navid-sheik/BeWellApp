package com.example.bewell.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Meal implements Parcelable {
    private String id;
    private String mealDate;
    private ArrayList<Food> foods;

    public Meal(String id, String mealDate, ArrayList<Food> foods) {
        this.id = id;
        this.mealDate = mealDate;
        this.foods = foods;
    }

    protected Meal(Parcel in) {
        id = in.readString();
        mealDate = in.readString();
        foods = in.createTypedArrayList(Food.CREATOR);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMealDate() {
        return mealDate;
    }

    public void setMealDate(String mealDate) {
        this.mealDate = mealDate;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(mealDate);
        dest.writeTypedList(foods);
    }
}
