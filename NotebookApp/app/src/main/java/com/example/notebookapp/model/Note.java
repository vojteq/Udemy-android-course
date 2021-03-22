package com.example.notebookapp.model;

import java.util.Date;

public class Note {
    private int id;
    private int quantity;
    private String productName;
    private String date;

    public Note(int id,String productName,  int quantity, Date date) {
        this.id = id;
        this.quantity = quantity;
        this.productName = productName;
        this.date = date.toString();
    }

    public Note(int id,String productName,  int quantity, String date) {
        this.id = id;
        this.quantity = quantity;
        this.productName = productName;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDate(Date date) {
        this.date = date.toString();
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getDate() {
        return date;
    }

    public String getStringDate() {
        return date.toString();
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", productName='" + productName + '\'' +
                ", date=" + date +
                '}';
    }
}
