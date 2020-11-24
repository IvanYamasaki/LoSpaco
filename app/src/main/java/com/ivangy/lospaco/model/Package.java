package com.ivangy.lospaco.model;

import java.util.ArrayList;

public class Package {

    private String namePackage, imgPackage;
    private ArrayList<Service> listServices;
    private double pricepackage;

    public Package(String namePackage, String imgPackage, ArrayList<Service> listServices, double pricepackage) {
        this.namePackage = namePackage;
        this.imgPackage = imgPackage;
        this.listServices = listServices;
        this.pricepackage = pricepackage;
    }

    public String getImgPackage() {
        return imgPackage;
    }

    public String getNamePackage() {
        return namePackage;
    }

    public ArrayList<Service> getListServices() {
        return listServices;
    }

    public double getPricepackage() {
        return pricepackage;
    }
}
