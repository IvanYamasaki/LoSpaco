package com.ivangy.lospaco.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Service implements Serializable {

    private String name, desc, img, category, clothing;
    private double price;
    private float starRating;
    private Time time;

    public Service(String name, String desc, float starRating, String img, String category, String clothing, Time time, double price) {
        this.name = name;
        this.desc = desc;
        this.starRating = starRating;
        this.img = img;
        this.category = category;
        this.price = price;
        this.clothing = clothing;
        this.time=time;
    }

    public String getClothing() {
        return clothing;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public float getStarRating() {
        return starRating;
    }

    public String getImg() {
        return img;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public Time getTime() {
        return time;
    }
}
