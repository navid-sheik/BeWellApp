package com.example.bewell.models;

public class User {
    private String name;
    private String surname;
    private String id;
    private String age;
    private String weight;
    private boolean type;
    public User(){

    }
    public User(String name, String surname,String id,boolean type) {
        this.name = name;
        this.surname = surname;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }
    public String getSurname() {
        return surname;
    }
    public boolean getType(){
        return type;
    }
    public String getWeight() {
        return weight;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
