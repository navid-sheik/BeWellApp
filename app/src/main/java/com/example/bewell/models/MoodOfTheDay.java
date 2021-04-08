package com.example.bewell.models;

import java.util.ArrayList;

public class MoodOfTheDay {

    private String date;
    private int valueStatus;

    private int additonalValueStatus;
    private  int totalValue;
    private ArrayList<MoodQuestion> moodQuestions;

    public MoodOfTheDay(String date, int valueStatus, ArrayList<MoodQuestion> moodQuestions , int additionalValueStatus ) {
        this.date = date;
        this.valueStatus = valueStatus;
        this.additonalValueStatus  =  additionalValueStatus;
        this.totalValue =  this.valueStatus + this.additonalValueStatus;
        this.moodQuestions = moodQuestions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValueStatus() {
        return valueStatus;
    }

    public void setValueStatus(int valueStatus) {
        this.valueStatus = valueStatus;
        this.totalValue = this.additonalValueStatus + this.valueStatus;
    }

    public ArrayList<MoodQuestion> getMoodQuestions() {
        return moodQuestions;
    }

    public void setMoodQuestions(ArrayList<MoodQuestion> moodQuestions) {
        this.moodQuestions = moodQuestions;
    }



    public void incrementAdditionalValue (int valueToIncrement){
        this.additonalValueStatus  += valueToIncrement;
        this.totalValue = this.additonalValueStatus + this.valueStatus;
    }


    public int getAdditonalValueStatus() {
        return additonalValueStatus;
    }

    public void setAdditonalValueStatus(int additonalValueStatus) {
        this.additonalValueStatus = additonalValueStatus;
    }


    public int getTotalValue() {
        return totalValue ;
    }
}
