package com.ivangy.lospaco.model;

public class ItemSlider {

    private String img, title, desc;

    public ItemSlider(String img, String title, String desc) {
        this.img = img;
        this.title = title;
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
