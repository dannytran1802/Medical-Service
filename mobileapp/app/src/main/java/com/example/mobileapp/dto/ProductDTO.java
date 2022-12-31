package com.example.mobileapp.dto;

public class ProductDTO {

    private long id;
    private String name;
    private String price;
    private String avatar;
    private String description;
    private long pharmacyId;
    private boolean otc; // Over-the-counter medicine

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
