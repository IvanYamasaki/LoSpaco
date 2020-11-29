package com.ivangy.lospaco.model;

public class Cart {

    private String nameItem, type;
    private int qntItem;

    public Cart(String nameItem, int qntItem, String type) {
        this.nameItem = nameItem;
        this.qntItem = qntItem;
        this.type= type;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

/*
    public void setPriceTotal() {
        this.priceTotal = Double.valueOf(String.format("%.2f", priceBase*qntItem).replace(",", "."));
    }
*/

    public String getNameItem() {
        return nameItem;
    }

    public int getQntItem() {
        return qntItem;
    }

    public void setQntItem(int qntItem) {
        this.qntItem = qntItem;
    }

}
