package com.example.mobileapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.R;
import com.example.mobileapp.api.LocationAPI;
import com.example.mobileapp.api.LoginAPI;
import com.example.mobileapp.api.TokenAPI;
import com.example.mobileapp.dto.AccountDTO;
import com.example.mobileapp.dto.LocationDTO;
import com.example.mobileapp.itf.LocationInterface;
import com.example.mobileapp.itf.LoginInterface;
import com.example.mobileapp.dto.AuthDTO;
import com.example.mobileapp.util.ContantUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginInterface, LocationInterface {

    EditText inputUsername;
    TextInputEditText inputPassword;
    TextView btnregister,btnfoget;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        click();

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    private void initView() {
        btnregister = findViewById(R.id.newAccount);
        btnLogin = findViewById(R.id.btnLogin);
        btnfoget = findViewById(R.id.forgotPassword);

        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
    }

    private void click() {
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentregister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentregister);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                if (username.length() > 0 && password.length() > 0) {
                    LoginAPI loginAPI = new LoginAPI(LoginActivity.this);
                    loginAPI.login(username, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter email and password!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnfoget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("medical-service-application")
                .setApplicationId("1:925224093205:android:620cd5fbace771ab0b701c")
                .setApiKey("AIzaSyCMhMF0QU__S3XM2Woa7GzZgTAZMlVdQMY")
                .build();

    }

    @Override
    public void loginSuccess(AuthDTO authDTO) {
        ContantUtil.authDTO = authDTO;
        ContantUtil.accessToken = authDTO.getAccessToken();
        ContantUtil.refreshToken = authDTO.getRefreshToken();

        Intent intent = null;
        switch (ContantUtil.roleName) {
            case "USER":
                intent = new Intent(getApplicationContext(), HomeUserActivity.class);
                break;
            case "PHARMACY":
                intent = new Intent(getApplicationContext(), HomePharmaActivity.class);
                break;
            case "AMBULANCE":
                intent = new Intent(getApplicationContext(), HomeAmbulanceActivity.class);
                break;
            default:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                break;
        }
        startActivity(intent);

        // locationD api
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
        locationDTO.setStatus(false);

        LocationAPI locationAPI = new LocationAPI(LoginActivity.this);
        locationAPI.updateStatus(locationDTO);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("LOG_TOKEN", token);

                        Toast.makeText(LoginActivity.this, "TOKEN: "+ token, Toast.LENGTH_SHORT).show();

                        AccountDTO accountDTO = new AccountDTO();
                        accountDTO.setId(Long.parseLong(authDTO.getAccountId()));
                        accountDTO.setToken(token);

                        TokenAPI tokenAPI = new TokenAPI();
                        tokenAPI.updateToken(accountDTO);
                    }
                });

        Toast.makeText(LoginActivity.this, "Hello, " + authDTO.getEmail(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(LoginActivity.this, "Username or password is incorrect!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessLocation() {

    }

    @Override
    public void onError(List<String> errors) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}