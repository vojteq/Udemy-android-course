package com.example.shoppinglist.model;

import java.util.Date;

public class Item {
    private int id;
    private String productName;
    private int quantity;
    private String date;

    public Item(String productName, int quantity, String date) {
        this.productName = productName;
        this.quantity = quantity;
        this.date = date;
    }

    public Item(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public Item(int id, String productName, int quantity, Date date) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.date = date.toString();
    }

    public Item(int id, String productName, int quantity, String date) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.date = date;
    }

    public Item() {
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDate(Date date) {
        this.date = date.toString();
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", date='" + date + '\'' +
                '}';
    }
}
