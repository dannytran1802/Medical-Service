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
import com.example.mobileapp.api.ForgotPassAPI;
import com.example.mobileapp.dto.AccountDTO;
import com.example.mobileapp.itf.ForgotPassInterface;

import java.util.List;

public class ForgetPasswordActivity extends AppCompatActivity implements ForgotPassInterface {

    private Button btnSubmit;
    private EditText inputUsername;
    private TextView btnSignIn, textMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
        click();

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void click() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputUsername.getText().toString();
                if (validator()) {
                    AccountDTO accountDTO = new AccountDTO();
                    accountDTO.setEmail(email);

                    ForgotPassAPI forgotPassAPI = new ForgotPassAPI(ForgetPasswordActivity.this);
                    forgotPassAPI.forgotPass(accountDTO);
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Please enter required fields!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intentlogin = new Intent(ForgetPasswordActivity.this, RegisterActivity.class);
                startActivity(intentlogin);
            }
        });
    }

    private void initView() {
        inputUsername = findViewById(R.id.inputUsername);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSignIn = findViewById(R.id.btnSignIn);

        textMsg = findViewById(R.id.textMsg);
    }

    @Override
    public void onSuccess() {
        finish();
        Intent intent =  new Intent(getApplicationContext(), CompleteForgotPassActivity.class);
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
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }

    private boolean validator() {
        String email = inputUsername.getText().toString();

        if (email.length() == 0) {
            return false;
        }

        return true;
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