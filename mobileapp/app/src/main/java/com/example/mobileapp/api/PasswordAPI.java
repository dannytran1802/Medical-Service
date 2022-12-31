package com.example.mobileapp.api;

import com.example.mobileapp.dto.AccountDTO;
import com.example.mobileapp.dto.ProfileDTO;
import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.itf.PasswordInterface;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordAPI {

    private PasswordInterface passwordInterface = null;

    private APIService apiService;

    public PasswordAPI(PasswordInterface passwordInterface) {
        this.passwordInterface = passwordInterface;
        apiService = APIClient.getAPIService();
    }

    public void changePass(ProfileDTO profileDTO) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.changePass(token, profileDTO).enqueue(new Callback<ReponseDTO>() {
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
                        passwordInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        passwordInterface.onSuccess();
                    }
                } else {
                    passwordInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO> call, Throwable t) {
                passwordInterface.onError(null);
            }
        });
    }

}
