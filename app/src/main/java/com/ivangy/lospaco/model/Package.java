package com.ivangy.lospaco.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Package {

    private int Id;
    private String Name, Image, Description;
    private ArrayList<Service> Services;
    private double Price;

    public Package(int id, String name, String image, String description, ArrayList<Service> services, double price) {
        Id = id;
        Name = name;
        Image = image;
        Description = description;
        Services = services;
        Price = price;
    }

    public Bitmap getImage() throws UnsupportedEncodingException {
        byte[] decodedString = Base64.decode(Image, Base64.DEFAULT);
        String text = new String(decodedString, "UTF-8");
        byte[] decodedText = Base64.decode(text, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedText, 0, decodedText.length);
        return decodedByte;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public ArrayList<Service> getServices() {
        return Services;
    }

    public double getPrice() {
        return Price;
    }
}
