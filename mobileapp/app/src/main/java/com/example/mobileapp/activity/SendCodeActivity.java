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

import java.util.List;

public class SendCodeActivity extends AppCompatActivity implements RegisterInterface {

    private EditText code1, code2, code3, code4;
    private TextView textMsg;
    private Button btnconfirm;
    private RegisterDTO registerDTO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_code);

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

    private void initView() {
        btnconfirm = findViewById(R.id.btnconfirm);
        code1 = findViewById(R.id.code1);
        code2 = findViewById(R.id.code2);
        code3 = findViewById(R.id.code3);
        code4 = findViewById(R.id.code4);
        textMsg = findViewById(R.id.textMsg);
    }

    private void click() {
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c1 = code1.getText().toString();
                String c2 = code2.getText().toString();
                String c3 = code3.getText().toString();
                String c4 = code4.getText().toString();
                if (validator()) {
                    registerDTO = ContantUtil.registerDTO;
                    registerDTO.setOtp(c1 + c2 + c3 + c4);

                    RegisterAPI registerAPI = new RegisterAPI(SendCodeActivity.this);
                    registerAPI.register(registerDTO);
                } else {
                    Toast.makeText(SendCodeActivity.this, "Please enter code!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validator() {
        String c1 = code1.getText().toString();
        String c2 = code2.getText().toString();
        String c3 = code3.getText().toString();
        String c4 = code4.getText().toString();
        if (c1.length() == 0 || c2.length() == 0 || c3.length() == 0 || c4.length() == 0) {
            return false;
        }

        return true;
    }

    @Override
    public void onSuccess() {
        finish();
        Intent intent =  new Intent(getApplicationContext(), CompleteSignUpActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}