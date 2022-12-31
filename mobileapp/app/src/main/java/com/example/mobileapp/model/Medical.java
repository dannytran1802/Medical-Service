package com.example.mobileapp.model;

public class Medical {

    private long id;
    private String name;
    private String address;
    private String contact;
    private boolean status;

    public Medical() {
    }

    public Medical(long id, String name, String address, String contact, boolean status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
