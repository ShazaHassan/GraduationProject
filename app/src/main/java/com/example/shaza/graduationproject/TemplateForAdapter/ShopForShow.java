package com.example.shaza.graduationproject.TemplateForAdapter;

/**
 * Created by Shaza Hassan on 28-Jan-18.
 */

public class ShopForShow {
    private int img, price;
    private String name, description;

    public ShopForShow() {
    }

    public ShopForShow(int img, int price, String name, String description) {
        this.img = img;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
