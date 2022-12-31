package com.example.mobileapp.activity.ambulance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.HomeUserActivity;
import com.example.mobileapp.activity.LoginActivity;
import com.example.mobileapp.activity.PasswordActivity;
import com.example.mobileapp.activity.pharmacy.PharmacyActivity;
import com.example.mobileapp.activity.user.UserActivity;
import com.example.mobileapp.api.ProfileAPI;
import com.example.mobileapp.dto.ProfileDTO;
import com.example.mobileapp.itf.ProfileInterface;
import com.example.mobileapp.model.Account;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class AmbulanceActivity extends AppCompatActivity implements ProfileInterface {

    TextView textHello, textMsg, btnSubmit, textChangePass;
    EditText inputFullName, inputEmail, inputPhone, inputAddress, inputNumberPlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

        initView();
        click();

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ProfileAPI profileAPI = new ProfileAPI(AmbulanceActivity.this);
        profileAPI.findProfileByAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void click() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String phone = inputPhone.getText().toString();
                String address = inputAddress.getText().toString();
                String numberPlate = inputNumberPlate.getText().toString();

                ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                profileDTO.setFullName(fullname);
                profileDTO.setEmail(email);
                profileDTO.setPhone(phone);
                profileDTO.setAddress(address);
                profileDTO.setNumberPlate(numberPlate);

                ProfileAPI profileAPI = new ProfileAPI(AmbulanceActivity.this);
                profileAPI.updateProfile(profileDTO);
            }
        });

        textChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        textHello = findViewById(R.id.textHello);
        btnSubmit = findViewById(R.id.btnSubmit);
        textMsg = findViewById(R.id.textMsg);
        inputFullName = findViewById(R.id.inputFullName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhone = findViewById(R.id.inputPhone);
        inputAddress = findViewById(R.id.inputAddress);
        inputNumberPlate = findViewById(R.id.inputNumberPlate);
        textChangePass = findViewById(R.id.textChangePass);
    }

    private void initData(Account account) {
        inputFullName.setText(account.getAmbulanceName());
        inputEmail.setText(account.getEmail());
        inputPhone.setText(account.getPhone());
        inputAddress.setText(account.getAddress());
        inputNumberPlate.setText(account.getNumberPlate());
        textHello.setText("Hi! " + account.getAmbulanceName());
        ContantUtil.authDTO.setNumberPlate(account.getNumberPlate());
        ContantUtil.authDTO.setFullName(account.getFullName());
    }

    @Override
    public void onSuccess(Account account) {
        initData(account);
        Toast.makeText(getApplicationContext(), "Record updated successfully!", Toast.LENGTH_LONG).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        if (item.getItemId() == R.id.profile) {
            Intent intent = null;
            switch (ContantUtil.roleName) {
                case "USER":
                    intent = new Intent(getApplicationContext(), UserActivity.class);
                    break;
                case "PHARMACY":
                    intent = new Intent(getApplicationContext(), PharmacyActivity.class);
                    break;
                case "AMBULANCE":
                    intent = new Intent(getApplicationContext(), AmbulanceActivity.class);
                    break;
                default:
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    break;
            }
            startActivity(intent);
        }

        if (item.getItemId() == R.id.logout) {
            // show message
            AlertDialog.Builder builder = new AlertDialog.Builder(AmbulanceActivity.this);

            // Setting message manually and performing action on button click
            builder.setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish(); // close this activity and return to preview activity (if there is any)
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            // Creating dialog box
            AlertDialog alert = builder.create();
            // Setting the title manually
            alert.setTitle("Medical Service");
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

}