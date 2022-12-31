package com.example.mobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mobileapp.R;
import com.example.mobileapp.api.RegisterAPI;
import com.example.mobileapp.itf.RegisterInterface;
import com.example.mobileapp.dto.RegisterDTO;
import com.example.mobileapp.util.ContantUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SignUpActivity extends AppCompatActivity implements RegisterInterface {

    private EditText inputName, inputUsername;
    private TextInputEditText inputPassword;
    private TextView btnLogin, textMsg;
    private Button btnSignup;

    private String roleName = "";

    private RegisterDTO registerDTO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();
        click();

        Intent intent = getIntent();
        roleName = intent.getStringExtra("roleName");

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initView() {
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        textMsg = findViewById(R.id.textMsg);
        inputName = findViewById(R.id.inputName);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
    }

    private void click() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentregister = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intentregister);
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validator()) {
                    String fullname = inputName.getText().toString();
                    String username = inputUsername.getText().toString();
                    String password = inputPassword.getText().toString();

                    registerDTO = new RegisterDTO();
                    registerDTO.setFullName(fullname);
                    registerDTO.setUsername(username);
                    registerDTO.setPassword(password);
                    registerDTO.setRoleName(roleName.toUpperCase());
                    registerDTO.setOtp("");

                    RegisterAPI registerAPI = new RegisterAPI(SignUpActivity.this);
                    registerAPI.register(registerDTO);
                } else {
                    Toast.makeText(SignUpActivity.this, "Please enter required fields!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validator() {
        String fullname = inputName.getText().toString();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        if (fullname.length() == 0 || username.length() == 0 || password.length() == 0) {
            return false;
        }

        return true;
    }

    @Override
    public void onSuccess() {
        ContantUtil.registerDTO = registerDTO;
        Intent intent = new Intent(getApplicationContext(), SendCodeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onError(List<String> errors) {
        String msg = "";
        if (errors != null) {
            for (String s : errors) {
                msg += getStringResourceByName(s) + "\n";
            }
        }
        textMsg.setText(msg);
    }

    private String getStringResourceByName(String aString) {
        try {
            String packageName = getPackageName();
            int resId = getResources().getIdentifier(aString, "string", packageName);
            return getString(resId);
        } catch (Exception ex) {
            return "";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}