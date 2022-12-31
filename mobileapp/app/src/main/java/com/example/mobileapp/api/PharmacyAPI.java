package com.example.mobileapp.api;

import com.example.mobileapp.dto.AccountDTO;
import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.itf.ForgotPassInterface;
import com.example.mobileapp.itf.PharmacyInterface;
import com.example.mobileapp.model.Pharmacy;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmacyAPI {

    private PharmacyInterface pharmacyInterface = null;

    private APIService apiService;

    public PharmacyAPI(PharmacyInterface pharmacyInterface) {
        this.pharmacyInterface = pharmacyInterface;
        apiService = APIClient.getAPIService();
    }

    public void findAllPharmacy() {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.findAllPharmacy(token).enqueue(new Callback<ReponseDTO<List<Pharmacy>>>() {
            @Override
            public void onResponse(Call<ReponseDTO<List<Pharmacy>>> call, Response<ReponseDTO<List<Pharmacy>>> response) {
                if (response.code() == 200) {
                    ReponseDTO reponseDTO = response.body();
                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.ERROR)) {
                        Map<String, Object> messages = response.body().getMessages();
                        List<String> errors = new ArrayList<>();
                        if (messages != null) {
                            for (Map.Entry<String, Object> entry : messages.entrySet()) {
                                errors.add(entry.getValue().toString());
                            }
                        }
                        pharmacyInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        List<Pharmacy> pharmacyList = (List<Pharmacy>) reponseDTO.getData();
                        pharmacyInterface.onSuccess(pharmacyList);
                    }
                } else {
                    pharmacyInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<List<Pharmacy>>> call, Throwable t) {
                pharmacyInterface.onError(null);
            }
        });
    }

}
