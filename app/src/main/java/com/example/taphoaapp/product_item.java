package com.example.taphoaapp;

import java.io.Serializable;

public class product_item implements Serializable {
      private String category, imageUrl , name , discount,mau, size;
      private int soluong, price , GiaGoc, numdat;
      private double soluong2;

    public product_item() {
    }

    public product_item(String category, String name, String mau, String size, int price, int numdat,double soluong2) {
        this.category = category;
        this.name = name;
        this.mau = mau;
        this.size = size;
        this.price = price;
        this.numdat = numdat;
        this.soluong = soluong;
    }

    public product_item(String category, String name, int price, int numdat,int soluong) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.numdat = numdat;
        this.soluong = soluong;
    }

    public product_item(String category, String imageUrl, String name, String discount, int soluong, int price, int giaGoc) {
        this.category = category;
        this.imageUrl = imageUrl;
        this.name = name;
        this.discount = discount;
        this.soluong = soluong;
        this.price = price;
        GiaGoc = giaGoc;
    }

    public product_item(String imageUrl, String name, String discount, int soluong, int giaGoc, int price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.discount = discount;
        this.soluong = soluong;
        this.price = price;
        GiaGoc = giaGoc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getFirstName(String text) {

        int index = text.indexOf(' ');

        if (index > -1) { // Check if there is more than one word.

            return text.substring(0, index).trim(); // Extract first word.

        } else {

            return text; // Text is the first word itself.
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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

    public int getGiaGoc() {
        return GiaGoc;
    }

    public void setGiaGoc(int giaGoc) {
        GiaGoc = giaGoc;
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

    public int getNumdat() {
        return numdat;
    }

    public void setNumdat(int numdat) {
        this.numdat = numdat;
    }
}

