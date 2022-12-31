package com.example.mobileapp.api;

import com.example.mobileapp.dto.LocationDTO;
import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.itf.LocationInterface;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationAPI {

    private LocationInterface locationInterface = null;

    private APIService apiService;

    public LocationAPI(LocationInterface locationInterface) {
        this.locationInterface = locationInterface;
        apiService = APIClient.getAPIService();
    }

    public void updateStatus(LocationDTO locationDTO) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.saveLocation(token, locationDTO).enqueue(new Callback<ReponseDTO>() {
            @Override
            public void onResponse(Call<ReponseDTO> call, Response<ReponseDTO> response) {
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
                        locationInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        locationInterface.onSuccessLocation();
                    }
                } else {
                    locationInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO> call, Throwable t) {
                locationInterface.onError(null);
            }
        });
    }

}
