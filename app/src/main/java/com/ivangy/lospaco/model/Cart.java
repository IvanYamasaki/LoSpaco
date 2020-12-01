package com.ivangy.lospaco.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class Cart {

    private String Name, Type, Image, Price;
    private int Qnt;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Bitmap getImage() throws UnsupportedEncodingException {
        byte[] decodedString = Base64.decode(Image, Base64.DEFAULT);
        String text = new String(decodedString, "UTF-8");
        byte[] decodedText = Base64.decode(text, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedText, 0, decodedText.length);
        return decodedByte;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getQnt() {
        return Qnt;
    }

    public void setQnt(int qnt) {
        Qnt = qnt;
    }
}
