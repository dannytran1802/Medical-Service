package com.example.mobileapp.dto;

public class BookingDTO {

    private long id;
    private AmbulanceDTO ambulanceDTO;
    private AccountDTO accountDTO;
    private String note;
    private String progress;
    private String timeOrder;
    private boolean status;

    private long ambulanceId;
    private long accountId;

    private String uuid;
    private long historyId;

    private double longitude;
    private double latitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AmbulanceDTO getAmbulanceDTO() {
        return ambulanceDTO;
    }

    public void setAmbulanceDTO(AmbulanceDTO ambulanceDTO) {
        this.ambulanceDTO = ambulanceDTO;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
