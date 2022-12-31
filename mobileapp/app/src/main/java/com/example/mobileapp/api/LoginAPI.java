package com.example.mobileapp.api;

import com.example.mobileapp.dto.AuthDTO;
import com.example.mobileapp.itf.LoginInterface;
import com.example.mobileapp.util.ContantUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAPI {

    private LoginInterface loginInterface = null;

    private APIService apiService;

    public LoginAPI(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
        apiService = APIClient.getAPIService();
    }

    public void login(String username, String password) {
        apiService.loginAccount(username, password, "password").enqueue(new Callback<AuthDTO>() {
            @Override
            public void onResponse(Call<AuthDTO> call, Response<AuthDTO> response) {
                if (response.code() == 200) {
                    AuthDTO account = response.body();
                    if (account == null) {
                        loginInterface.loginError(null);
                    } else {
                        ContantUtil.roleName = account.getRole().toUpperCase();
                        loginInterface.loginSuccess(account);
                    }
                } else {
                    loginInterface.loginError(null);
                }
            }

            @Override
            public void onFailure(Call<AuthDTO> call, Throwable t) {
                loginInterface.loginError(t.getMessage());
            }
        });
    }

}
