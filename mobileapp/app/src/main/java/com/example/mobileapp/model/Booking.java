package com.example.mobileapp.model;

public class Booking {

    private long id;
    private Ambulance ambulanceDTO;
    private Account accountDTO;
    private String progress;
    private String timeOrder;
    private boolean status;

    private long ambulanceId;
    private long accountId;

    private String uuid;
    private String note;

    protected String createdOn;
    protected String updatedOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Ambulance getAmbulanceDTO() {
        return ambulanceDTO;
    }

    public void setAmbulanceDTO(Ambulance ambulanceDTO) {
        this.ambulanceDTO = ambulanceDTO;
    }

    public Account getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(Account accountDTO) {
        this.accountDTO = accountDTO;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getAmbulanceId() {
        return ambulanceId;
    }

    public void setAmbulanceId(long ambulanceId) {
        this.ambulanceId = ambulanceId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
