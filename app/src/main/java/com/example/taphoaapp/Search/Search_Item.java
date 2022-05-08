package com.example.taphoaapp.Search;

public class Search_Item {
    private String Name;
    private String Category;

    public Search_Item(String name, String category) {
        Name = name;
        Category = category;
    }

    public Search_Item() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
