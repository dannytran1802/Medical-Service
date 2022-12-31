package com.example.mobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mobileapp.R;
import com.example.mobileapp.api.PasswordAPI;
import com.example.mobileapp.dto.ProfileDTO;
import com.example.mobileapp.itf.PasswordInterface;
import com.example.mobileapp.util.ContantUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class PasswordActivity extends AppCompatActivity implements PasswordInterface {

    private Button btnSubmit;
    private TextInputEditText inputOldPass, inputNewPass, inputReNewPass;
    private TextView textMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

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
                if (validator()) {
                    String oldPass = inputOldPass.getText().toString();
                    String newPass = inputNewPass.getText().toString();

                    ProfileDTO profileDTO = new ProfileDTO();
                    profileDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                    profileDTO.setOldPass(oldPass);
                    profileDTO.setNewPass(newPass);

                    PasswordAPI passwordAPI = new PasswordAPI(PasswordActivity.this);
                    passwordAPI.changePass(profileDTO);
                } else {
                    Toast.makeText(PasswordActivity.this, "Please enter required fields!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initView() {
        inputOldPass = findViewById(R.id.inputOldPass);
        inputNewPass = findViewById(R.id.inputNewPass);
        inputReNewPass = findViewById(R.id.inputReNewPass);
        btnSubmit = findViewById(R.id.btnSubmit);
        textMsg = findViewById(R.id.textMsg);
    }

    @Override
    public void onSuccess() {
        finish();
        Intent intent =  new Intent(getApplicationContext(), CompleteChangePassActivity.class);
        startActivity(intent);
    }

    @Override
    public void onError(List<String> errors) {
        String msg = "The password is incorrect";
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
        String oldPass = inputOldPass.getText().toString();
        String newPass = inputNewPass.getText().toString();
        String reNewPass = inputReNewPass.getText().toString();

        if (oldPass.length() == 0 || newPass.length() == 0 || reNewPass.length() == 0) {
            return false;
        } else {
            if (newPass.length() < 6) {
                textMsg.setText("New password > 6 characters!");
                return false;
            } else {
                if (!newPass.equals(reNewPass)) {
                    textMsg.setText("Re-entered password is incorrect!");
                    return false;
                }
            }
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