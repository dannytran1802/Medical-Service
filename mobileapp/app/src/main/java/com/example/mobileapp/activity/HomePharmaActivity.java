package com.example.mobileapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.ambulance.AmbulanceActivity;
import com.example.mobileapp.activity.pharmacy.PharmacyActivity;
import com.example.mobileapp.activity.pharmacy.PharmacyHistoryActivity;
import com.example.mobileapp.activity.pharmacy.ProductListActivity;
import com.example.mobileapp.activity.user.UserActivity;
import com.example.mobileapp.util.ContantUtil;

public class HomePharmaActivity extends AppCompatActivity {

    LinearLayout btnpharma, btnperson, btnbill;
    LinearLayout btnVideoCall, btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pharma);

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
    }

    private void click() {
        btnpharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePharmaActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });

        btnperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePharmaActivity.this, PharmacyActivity.class);
                startActivity(intent);
            }
        });

        btnbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePharmaActivity.this, PharmacyHistoryActivity.class);
                startActivity(intent);
            }
        });

        btnVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePharmaActivity.this, VideoCallActivity.class);
                startActivity(intent);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePharmaActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btnpharma = findViewById(R.id.pharma);
        btnperson = findViewById(R.id.personbtn);
        btnbill = findViewById(R.id.billbtn);
        btnVideoCall = findViewById(R.id.btnVideoCall);
        btnChat = findViewById(R.id.btnChat);
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
            finish(); // close this activity and return to preview activity (if there is any)

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
            AlertDialog.Builder builder = new AlertDialog.Builder(HomePharmaActivity.this);

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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }


}