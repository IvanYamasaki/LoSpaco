package com.ivangy.lospaco.model;

public class Employee {

    private String name, image, email, phoneNumber;
    private char gender;
    private float starRating;
    private int timesWorked, age;

    public Employee(String name, String image, char gender, String email, String phoneNumber, float starRating, int timesWorked, int age) {
        this.name = name;
        this.image = image;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.starRating=starRating;
        this.age=age;
        this.timesWorked=timesWorked;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public char getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public float getStarRating() {
        return starRating;
    }

    public int getTimesWorked() {
        return timesWorked;
    }

    public int getAge() {
        return age;
    }
}
