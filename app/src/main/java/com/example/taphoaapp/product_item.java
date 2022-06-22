package com.example.taphoaapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class product_item implements Serializable {
      private String category, imageUrl , name , discount,mau, size;
      String IMAGE1,IMAGE2,IMAGE3,SANPHAM_ID,MoTa;
      private int soluong, price , GiaGoc, numdat;
      private double soluong2;
      private List<String> COLOR = new ArrayList<String>();
    private List<String> SIZE = new ArrayList<String>();

    public product_item() {
    }

    //    FOR ADD all SAN PHAM
    public product_item(String category,String discount,int price, int GiaGoc,String imageUrl,String IMAGE1,String IMAGE2,String IMAGE3,List<String> COLOR,String MoTa, String name,String SANPHAM_ID,List<String> SIZE,int soluong) {
        this.category = category;
        this.discount = discount;
        this.price = price;
        this.GiaGoc = GiaGoc;
        this.imageUrl = imageUrl;
        this.IMAGE1 =  IMAGE1;
        this.IMAGE2 =  IMAGE2;
        this.IMAGE3 =  IMAGE3;
        this.COLOR = COLOR;
        this.MoTa = MoTa;
        this.name = name;
        this.SANPHAM_ID = SANPHAM_ID;
        this.SIZE = SIZE;
        this.soluong = soluong;
    }

    //    FOR ADD CHI TIET SAN PHAM
    public product_item(String imageUrl,String IMAGE2,String IMAGE3,List<String> COLOR,String MoTa, String name,String SANPHAM_ID,List<String> SIZE) {
        this.imageUrl = imageUrl;
        this.IMAGE2 =  IMAGE2;
        this.IMAGE3 =  IMAGE3;
        this.COLOR = COLOR;
        this.MoTa = MoTa;
        this.name = name;
        this.SANPHAM_ID = SANPHAM_ID;
        this.SIZE = SIZE;
    }

//    FOR ADD SAN PHAM
    public product_item(String category, String imageUrl, String name, String discount, int soluong, int price, int giaGoc,String SANPHAM_ID) {
        this.category = category;
        this.imageUrl = imageUrl;
        this.name = name;
        this.discount = discount;
        this.soluong = soluong;
        this.price = price;
        GiaGoc = giaGoc;
        this.SANPHAM_ID = SANPHAM_ID;
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

    public String getIMAGE2() {
        return IMAGE2;
    }

    public void setIMAGE2(String IMAGE2) {
        this.IMAGE2 = IMAGE2;
    }

    public String getIMAGE3() {
        return IMAGE3;
    }

    public void setIMAGE3(String IMAGE3) {
        this.IMAGE3 = IMAGE3;
    }

    public String getSANPHAM_ID() {
        return SANPHAM_ID;
    }

    public void setSANPHAM_ID(String SANPHAM_ID) {
        this.SANPHAM_ID = SANPHAM_ID;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public List<String> getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(List<String> COLOR) {
        this.COLOR = COLOR;
    }

    public List<String> getSIZE() {
        return SIZE;
    }

    public void setSIZE(List<String> SIZE) {
        this.SIZE = SIZE;
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

    public String getIMAGE1() {
        return IMAGE1;
    }

    public void setIMAGE1(String IMAGE1) {
        this.IMAGE1 = IMAGE1;
    }
}

