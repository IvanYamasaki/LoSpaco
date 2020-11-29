package com.ivangy.lospaco.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class Service implements Serializable {

    private int Id;
    private String Name, MinifiedDesc, CompleteDesc, PropperClothing, StarRating, Time, Image;
    private double Price;
    private Category Category;

    public Service(int id, String name, String minifiedDesc, String completeDesc,
                   String propperClothing, String image, double price, String starRating,
                   String time, com.ivangy.lospaco.model.Category category) {
        Id = id;
        Name = name;
        MinifiedDesc = minifiedDesc;
        CompleteDesc = completeDesc;
        PropperClothing = propperClothing;
        Image = image;
        Price = price;
        StarRating = starRating;
        Time = time;
        Category = category;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMinifiedDesc() {
        return MinifiedDesc;
    }

    public String getCompleteDesc() {
        return CompleteDesc;
    }

    public String getPropperClothing() {
        return PropperClothing;
    }

    public void setImage(String image) {
        Image = image;
    }

    public double getPrice() {
        return Price;
    }

    public String getStarRating() {
        return StarRating;
    }

    public Bitmap getImage() throws UnsupportedEncodingException {
        byte[] decodedString = Base64.decode(Image, Base64.DEFAULT);
        String text = new String(decodedString, "UTF-8");
        byte[] decodedText = Base64.decode(text, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedText, 0, decodedText.length);
        return decodedByte;
    }

    public String getTime() {
        return Time;
    }
}
