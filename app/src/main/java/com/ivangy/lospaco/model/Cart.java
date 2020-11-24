package com.ivangy.lospaco.model;

public class Cart {

    private String nameItem;
    private int qntItem;
    private double priceTotal, priceBase;

    public Cart(String nameItem, int qntItem, double priceBase) {
        this.nameItem = nameItem;
        this.qntItem = qntItem;
        this.priceBase=priceBase;
        setPriceTotal();
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal() {
        this.priceTotal = Double.valueOf(String.format("%.2f", priceBase*qntItem).replace(",", "."));
    }

    public String getNameItem() {
        return nameItem;
    }

    public int getQntItem() {
        return qntItem;
    }

    public void setQntItem(int qntItem) {
        this.qntItem = qntItem;
        setPriceTotal();
    }

    public double getPriceBase() {
        return priceBase;
    }

    public void setPriceBase(double priceBase) {
        this.priceBase = priceBase;
    }
}
