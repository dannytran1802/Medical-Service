package com.example.mobileapp.activity.pharmacy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.HomeAmbulanceActivity;
import com.example.mobileapp.activity.HomePharmaActivity;
import com.example.mobileapp.activity.HomeUserActivity;
import com.example.mobileapp.activity.LoginActivity;
import com.example.mobileapp.activity.ambulance.AmbulanceActivity;
import com.example.mobileapp.activity.user.UserActivity;
import com.example.mobileapp.api.LocationAPI;
import com.example.mobileapp.api.ProductAPI;
import com.example.mobileapp.dto.LocationDTO;
import com.example.mobileapp.dto.ProductDTO;
import com.example.mobileapp.itf.ProductInterface;
import com.example.mobileapp.model.Account;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class ProductFormActivity extends AppCompatActivity implements ProductInterface {

    Button btnUpdate;

    long productId, pharmacyId;
    String productName;
    String price;
    String description;
    boolean otc;

    //set view
    EditText inputName, inputPrice, inputDescription;
    TextView textProductList;
    Switch switchOTC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

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

        Intent intent = getIntent();
        productId = intent.getLongExtra("productId", 0);
        pharmacyId = intent.getLongExtra("pharmacyId", 0);
        productName = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        description = intent.getStringExtra("description");
        otc = intent.getBooleanExtra("otc", false);
        inputName.setText(productName);
        inputPrice.setText(price);
        inputDescription.setText(description);
        switchOTC.setChecked(otc);
    }

    private void click() {
        switchOTC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                otc = isChecked;
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String price = inputPrice.getText().toString();
                String description = inputDescription.getText().toString();

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(productId);
                productDTO.setPharmacyId(pharmacyId);
                productDTO.setName(name);
                productDTO.setPrice(price);
                productDTO.setDescription(description);
                productDTO.setOtc(otc);

                ProductAPI productAPI = new ProductAPI(ProductFormActivity.this);
                productAPI.updateProduct(productDTO);
            }
        });

        textProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ProductFormActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btnUpdate = findViewById(R.id.btnAddPro);
        inputName = findViewById(R.id.inputName);
        inputPrice = findViewById(R.id.inputPrice);
        inputDescription = findViewById(R.id.inputDescrip);
        switchOTC = findViewById(R.id.switchOTC);
        textProductList = findViewById(R.id.textProductList);
    }

    @Override
    public void onSuccess(List<Product> productList) {
        initData(productList);
        Toast.makeText(getApplicationContext(), "Record updated successfully!", Toast.LENGTH_LONG).show();
    }

    private void initData(List<Product> productList) {
        if (productList != null && !productList.isEmpty()) {
            Product product = productList.get(0);
            inputName.setText(product.getName());
            inputPrice.setText(product.getPrice());
            inputDescription.setText(product.getDescription());
            switchOTC.setChecked(product.isOtc());
        }
    }

    @Override
    public void onError(List<String> errors) {
        String msg = "";
        if (errors != null) {
            for (String s : errors) {
                msg += getStringResourceByName(s) + "\n";
            }
        }
        //textMsg.setText(msg);
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_LONG).show();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ProductFormActivity.this);

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