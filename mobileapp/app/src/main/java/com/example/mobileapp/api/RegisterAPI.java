package com.example.mobileapp.api;

import com.example.mobileapp.dto.RegisterDTO;
import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.itf.RegisterInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAPI {

    private RegisterInterface registerInterface = null;

    private APIService apiService;

    public RegisterAPI(RegisterInterface registerInterface) {
        this.registerInterface = registerInterface;
        apiService = APIClient.getAPIService();
    }

    public void register(RegisterDTO registerDTO) {
        apiService.registerAccount(registerDTO).enqueue(new Callback<ReponseDTO>() {
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
                        registerInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        registerInterface.onSuccess();
                    }
                } else {
                    registerInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO> call, Throwable t) {
                registerInterface.onError(null);
            }
        });
    }

}
