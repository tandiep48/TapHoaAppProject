package com.example.taphoaapp;

import java.io.Serializable;

public class basket_product_item implements Serializable {
      private String category,  name , mau, size,ID;
      private int soluong, price ,  numdat;

    public basket_product_item() {
    }

    public basket_product_item(String ID,String category, String name, String mau, String size, int soluong, int price, int numdat) {
        this.category = category;
        this.name = name;
        this.mau = mau;
        this.size = size;
        this.soluong = soluong;
        this.price = price;
        this.numdat = numdat;
        this.ID= ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumdat() {
        return numdat;
    }

    public void setNumdat(int numdat) {
        this.numdat = numdat;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

