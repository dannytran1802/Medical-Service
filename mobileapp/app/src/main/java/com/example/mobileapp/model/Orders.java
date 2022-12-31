package com.example.mobileapp.model;

import java.util.List;

public class Orders {

    private long id;
    private String progress;
    private String address;
    private String totalCost;
    private boolean status;

    private long accountId;
    private Account accountDTO;

    private long pharmacyId;
    private Pharmacy pharmacyDTO;

    private List<OrderDetails> orderDetails;

    protected String createdOn;
    protected String updatedOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Account getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(Account accountDTO) {
        this.accountDTO = accountDTO;
    }

    public long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Pharmacy getPharmacyDTO() {
        return pharmacyDTO;
    }

    public void setPharmacyDTO(Pharmacy pharmacyDTO) {
        this.pharmacyDTO = pharmacyDTO;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }
}
