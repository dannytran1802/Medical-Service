package com.example.mobileapp.model;

public class Pharmacy {

    private long id;
    private String name;
    private String address;
    private boolean status;

    public Pharmacy() {
    }

    public Pharmacy(long id, String name, String address, boolean status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.status = status;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
