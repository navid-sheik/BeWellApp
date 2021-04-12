package com.example.bewell.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String userId;
    private String empId;
    private String name;
    private String surname;
    private String email;
    private boolean employeeType;
    private String age;
    private String weight;
    private String height;


    public User(){

    }

    public User(String userId,String empId, String name, String surname, boolean employeeType, String age, String weight, String height) {
        this.userId = userId;
        this.empId = empId;
        this.name = name;
        this.surname = surname;
        this.employeeType = employeeType;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }


    public User(String userId, String empId, String name, String surname, String email,  boolean employeeType) {
        this.userId = userId;
        this.empId = empId;
        this.name = name;
        this.surname = surname;
        this.employeeType = employeeType;
        this.email =  email;
    }


    protected User(Parcel in) {
        userId = in.readString();
        empId = in.readString();
        name = in.readString();
        surname = in.readString();
        email = in.readString();
        employeeType = in.readByte() != 0;
        age = in.readString();
        weight = in.readString();
        height = in.readString();
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
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

    @Override
    public int describeContents() {
        return 0;
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
        dest.writeString(weight);
        dest.writeString(height);
    }
}
