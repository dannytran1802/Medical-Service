package com.example.mobileapp.itf;

import com.example.mobileapp.model.Ambulance;

import java.util.List;

public interface AmbulanceInterface {

    void onSuccess(List<Ambulance> ambulanceList);

    void onError(List<String> errors);

}
