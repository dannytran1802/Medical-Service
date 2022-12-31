package com.example.mobileapp.api;

import com.example.mobileapp.dto.ProfileDTO;
import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.itf.ProfileInterface;
import com.example.mobileapp.model.Account;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileAPI {

    private ProfileInterface profileInterface = null;

    private APIService apiService;

    public ProfileAPI(ProfileInterface profileInterface) {
        this.profileInterface = profileInterface;
        apiService = APIClient.getAPIService();
    }

    public void findProfileByAccountId(long id) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.findProfileByAccountId(token, id).enqueue(new Callback<ReponseDTO<Account>>() {
            @Override
            public void onResponse(Call<ReponseDTO<Account>> call, Response<ReponseDTO<Account>> response) {
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
                        profileInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        Account account = (Account) reponseDTO.getData();
                        profileInterface.onSuccess(account);
                    }
                } else {
                    profileInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<Account>> call, Throwable t) {
                profileInterface.onError(null);
            }
        });
    }

    public void updateProfile(ProfileDTO profileDTO) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.updateProfile(token, profileDTO).enqueue(new Callback<ReponseDTO<Account>>() {
            @Override
            public void onResponse(Call<ReponseDTO<Account>> call, Response<ReponseDTO<Account>> response) {
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
                        profileInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        Account account = (Account) reponseDTO.getData();
                        profileInterface.onSuccess(account);
                    }
                } else {
                    profileInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<Account>> call, Throwable t) {
                profileInterface.onError(null);
            }
        });
    }

}
