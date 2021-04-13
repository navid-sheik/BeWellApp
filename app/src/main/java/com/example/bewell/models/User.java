package com.example.bewell.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {
    private String userId;
    private String empId;
    private String name;
    private String surname;
    private String email;
    private boolean employeeType;
    private String age;
    private int weight;
    private int height;
    private int totalCalories;
    private int totalCaloriesBurned;
    private Meal brekfast;
    private Meal lunch;
    private Meal dinner;
    private ArrayList<Exercise> exercises;


    public User(){

    }

    public User(String userId, String empId, String name, String surname, String email, boolean employeeType, String age, int weight, int height, int totalCalories, int totalCaloriesBurned) {
        this.userId = userId;
        this.empId = empId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.employeeType = employeeType;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.totalCalories = totalCalories;
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public User(String userId, String empId, String name, String surname, String email, boolean employeeType) {
        this.userId = userId;
        this.empId = empId;
        this.name = name;
        this.surname = surname;
        this.employeeType = employeeType;
        this.email =  email;
        if (employeeType == false){
            this.totalCalories =  0;
            this.totalCaloriesBurned =  0;
            this.weight =  0;
            this.height =  0;
            this.brekfast =  new Meal();
            this.lunch = new Meal();
            this.dinner = new Meal();
            this.exercises = new ArrayList<>();

        }


    }


    protected User(Parcel in) {
        userId = in.readString();
        empId = in.readString();
        name = in.readString();
        surname = in.readString();
        email = in.readString();
        employeeType = in.readByte() != 0;
        age = in.readString();
        weight = in.readInt();
        height = in.readInt();
        totalCalories = in.readInt();
        totalCaloriesBurned = in.readInt();
        brekfast = in.readParcelable(Meal.class.getClassLoader());
        lunch = in.readParcelable(Meal.class.getClassLoader());
        dinner = in.readParcelable(Meal.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(empId);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(email);
        dest.writeByte((byte) (employeeType ? 1 : 0));
        dest.writeString(age);
        dest.writeInt(weight);
        dest.writeInt(height);
        dest.writeInt(totalCalories);
        dest.writeInt(totalCaloriesBurned);
        dest.writeParcelable(brekfast, flags);
        dest.writeParcelable(lunch, flags);
        dest.writeParcelable(dinner, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public int getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(int totalCaloriesBurned) {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(boolean employeeType) {
        this.employeeType = employeeType;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Meal getBrekfast() {
        return brekfast;
    }

    public void setBrekfast(Meal brekfast) {
        this.brekfast = brekfast;
    }

    public Meal getLunch() {
        return lunch;
    }

    public void setLunch(Meal lunch) {
        this.lunch = lunch;
    }

    public Meal getDinner() {
        return dinner;
    }

    public void setDinner(Meal dinner) {
        this.dinner = dinner;
    }
}
