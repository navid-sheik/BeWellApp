package com.example.bewell.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private String name;
    private int sets;
    private int repetitions;
    private int duration;
    private int caloriesBurned;

    public Exercise(String name, int sets, int repetitions, int duration, int caloriesBurned) {
        this.name = name;
        this.sets = sets;
        this.repetitions = repetitions;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

    protected Exercise(Parcel in) {
        name = in.readString();
        sets = in.readInt();
        repetitions = in.readInt();
        duration = in.readInt();
        caloriesBurned = in.readInt();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(sets);
        dest.writeInt(repetitions);
        dest.writeInt(duration);
        dest.writeInt(caloriesBurned);
    }
}
