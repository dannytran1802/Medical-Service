package com.example.mobileapp.api;

import com.example.mobileapp.dto.AccountDTO;
import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.itf.ForgotPassInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassAPI {

    private ForgotPassInterface forgotPassInterface = null;

    private APIService apiService;

    public ForgotPassAPI(ForgotPassInterface forgotPassInterface) {
        this.forgotPassInterface = forgotPassInterface;
        apiService = APIClient.getAPIService();
    }

    public void forgotPass(AccountDTO accountDTO) {
        apiService.forgotPasss(accountDTO).enqueue(new Callback<ReponseDTO>() {
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
                        forgotPassInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        forgotPassInterface.onSuccess();
                    }
                } else {
                    forgotPassInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO> call, Throwable t) {
                forgotPassInterface.onError(null);
            }
        });
    }

}
