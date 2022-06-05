package com.example.taphoaapp;

public class User {
    public String fullname, age, email, phone;
    public String gender;
    public  User(){

    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User(String fullname, String age, String email) {
        this.fullname = fullname;
        this.age = age;
        this.email = email;
    }

    public User(String fullname, String age, String email, String gender){
        this.fullname = fullname;
        this.age = age;
        this.email = email;
        this.gender = gender;
    }

    public User(String fullname, String age, String email, String phone, String gender) {
        this.fullname = fullname;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }
}
