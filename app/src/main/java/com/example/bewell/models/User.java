package com.example.bewell.models;

public class User {
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

    public User(String empId, String name, String surname, boolean employeeType, String age, String weight, String height) {
        this.empId = empId;
        this.name = name;
        this.surname = surname;
        this.employeeType = employeeType;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }


    public User(String empId, String name, String surname, String email,  boolean employeeType) {
        this.empId = empId;
        this.name = name;
        this.surname = surname;
        this.employeeType = employeeType;
        this.email =  email;
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
}
