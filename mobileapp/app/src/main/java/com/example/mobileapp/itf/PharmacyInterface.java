package com.example.mobileapp.itf;

import com.example.mobileapp.model.Pharmacy;

import java.util.List;

public interface PharmacyInterface {

    void onSuccess(List<Pharmacy> pharmacyList);

    void onError(List<String> errors);

}
