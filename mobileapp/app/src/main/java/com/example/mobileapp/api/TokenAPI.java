package com.example.mobileapp.api;

import com.example.mobileapp.dto.AccountDTO;
import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenAPI {

    private APIService apiService;

    public TokenAPI() {
        apiService = APIClient.getAPIService();
    }

    public void updateToken(AccountDTO accountDTO) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.saveToken(token, accountDTO).enqueue(new Callback<ReponseDTO>() {
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
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {

                    }
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO> call, Throwable t) {

            }
        });
    }

}
