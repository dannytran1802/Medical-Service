package com.example.mobileapp.model;

import java.io.Serializable;

public class Product implements Serializable {

    private long id;
    private String name;
    private String price;
    private String description;
    private long pharmacyId;
    private boolean otc;

    // custom
    private int qty;
    private boolean isAddToCart;

    public Product() {
    }

    public Product(long id, String name, String price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public boolean isAddToCart() {
        return isAddToCart;
    }

    public void setAddToCart(boolean addToCart) {
        isAddToCart = addToCart;
    }

    public long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public boolean isOtc() {
        return otc;
    }

    public void setOtc(boolean otc) {
        this.otc = otc;
    }
}
