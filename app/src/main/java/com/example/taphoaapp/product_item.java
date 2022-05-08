package com.example.taphoaapp;

public class product_item {
      private String imageUrl , name , discount;
      private int soluong, price , GiaGoc;

    public product_item() {
    }

    public product_item(String imageUrl, String name, String discount, int soluong, int giaGoc, int price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.discount = discount;
        this.soluong = soluong;
        this.price = price;
        GiaGoc = giaGoc;
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
}

