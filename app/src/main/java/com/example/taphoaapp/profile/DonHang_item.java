package com.example.taphoaapp.profile;

import com.google.type.DateTime;

import java.io.Serializable;
import java.util.Date;

public class DonHang_item implements Serializable {
      private String MaDH, status,time;
      private int soluong,pay;


    public DonHang_item() {
    }

    public DonHang_item(String maDH, String status, int soluong, int pay, String time) {
        MaDH = maDH;
        this.status = status;
        this.soluong = soluong;
        this.pay = pay;
        this.time = time;
    }

    public String getMaDH() {
        return MaDH;
    }

    public void setMaDH(String maDH) {
        MaDH = maDH;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

