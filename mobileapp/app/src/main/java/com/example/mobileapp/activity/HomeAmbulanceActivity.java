package com.example.mobileapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.ambulance.AmbulanceActivity;
import com.example.mobileapp.activity.ambulance.AmbulanceHistoryActivity;
import com.example.mobileapp.activity.pharmacy.PharmacyActivity;
import com.example.mobileapp.activity.user.UserActivity;
import com.example.mobileapp.api.BookingAPI;
import com.example.mobileapp.api.LocationAPI;
import com.example.mobileapp.dto.BookingDTO;
import com.example.mobileapp.dto.LocationDTO;
import com.example.mobileapp.itf.BookingInterface;
import com.example.mobileapp.itf.LocationInterface;
import com.example.mobileapp.model.Booking;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class HomeAmbulanceActivity extends AppCompatActivity implements LocationInterface, LocationListener, BookingInterface {

    TextView textNumberCar, textPoint, textLocation, btnViewList, btnMap, btnSubmit, textUser;
    Switch switchLocation;

    CountDownTimer countDownTimer = null;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 30s

    Booking booking = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ambulance);

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

        switchLocation.setChecked(false);
        setBookingActive(false);

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setBookingActive(isChecked);
            }
        });

        // booking api
        BookingAPI bookingAPI = new BookingAPI(HomeAmbulanceActivity.this);
        bookingAPI.findAllBookingByAmbulanceAndProgress(Long.parseLong(ContantUtil.authDTO.getAccountId()));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
        gps_enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        network_enabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } else if (network_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }

        textUser.setText(ContantUtil.authDTO.getFullName());
    }

    private void click() {
        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAmbulanceActivity.this, AmbulanceHistoryActivity.class);
                startActivity(intent);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAmbulanceActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show message
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeAmbulanceActivity.this);
                builder.setTitle("Medical Service");

                // Setting message manually and performing action on button click
                builder.setMessage("Are you want completed now?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // booking api
                                BookingAPI bookingAPI = new BookingAPI(HomeAmbulanceActivity.this);
                                bookingAPI.completed(booking.getId());
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
        });

        btnSubmit.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        btnViewList = findViewById(R.id.btnViewList);
        textNumberCar = findViewById(R.id.textNumberCar);
        textPoint = findViewById(R.id.textPoint);
        textLocation = findViewById(R.id.textLocation);
        switchLocation = findViewById(R.id.switchLocation);
        btnMap = findViewById(R.id.btnMap);
        btnSubmit = findViewById(R.id.btnSubmit);
        textUser = findViewById(R.id.textUser);

        if (ContantUtil.authDTO.getNumberPlate() != null) {
            textNumberCar.setText("License plates: " + ContantUtil.authDTO.getNumberPlate());
        }
    }

    @Override
    public void onSuccessLocation() {

    }

    @Override
    public void onBookingPending() {
        // booking api
        BookingAPI bookingAPI = new BookingAPI(HomeAmbulanceActivity.this);
        bookingAPI.findAllBookingByAmbulanceAndProgress(Long.parseLong(ContantUtil.authDTO.getAccountId()));
    }

    @Override
    public void onListBooking(List<Booking> bookingList) {
        if (bookingList != null && !bookingList.isEmpty()) {
            booking = bookingList.get(0);
            textPoint.setText(booking.getAccountDTO().getFullName() + " - " + booking.getAccountDTO().getPhone());
            textLocation.setText(booking.getNote());

            btnSubmit.setVisibility(View.VISIBLE);
        } else {
            booking = null;
            textPoint.setText("");
            textLocation.setText("");

            btnSubmit.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onError(List<String> errors) {

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getExtras().getString("title");
            String body = intent.getExtras().getString("body");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // show message
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeAmbulanceActivity.this);
                    builder.setMessage(body).setTitle("Medical Service");

                    // Setting message manually and performing action on button click
                    builder.setMessage(body)
                            .setCancelable(false)
                            .setPositiveButton("I agree", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    BookingDTO bookingDTO = new BookingDTO();
                                    bookingDTO.setHistoryId(Long.parseLong(title));
                                    bookingDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                                    bookingDTO.setProgress("APPROVED");

                                    BookingAPI bookingAPI = new BookingAPI(HomeAmbulanceActivity.this);
                                    bookingAPI.saveBooking(bookingDTO);

                                    bookingAPI.findAllBookingByAmbulanceAndProgress(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                                }
                            })
                            .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    BookingDTO bookingDTO = new BookingDTO();
                                    bookingDTO.setHistoryId(Long.parseLong(title));
                                    bookingDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                                    bookingDTO.setProgress("CANCELED");

                                    BookingAPI bookingAPI = new BookingAPI(HomeAmbulanceActivity.this);
                                    bookingAPI.saveBooking(bookingDTO);

                                    dialog.cancel();
                                }
                            });
                    // Creating dialog box
                    AlertDialog alert = builder.create();
                    // Setting the title manually
                    alert.setTitle("Medical Service");
                    alert.show();
                }
            });

            countDownTimer = new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    countDownTimer.cancel();
                }
            };
            countDownTimer.start();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("MyMessage")
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOG_LOCATION", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        ContantUtil.latitude = location.getLatitude();
        ContantUtil.longitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
//        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
//        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
//        Log.d("Latitude","status");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContantUtil.authDTO.getNumberPlate() != null) {
            textNumberCar.setText("License plates: " + ContantUtil.authDTO.getNumberPlate());
        }

        textUser.setText(ContantUtil.authDTO.getFullName());

        // booking api
        BookingAPI bookingAPI = new BookingAPI(HomeAmbulanceActivity.this);
        bookingAPI.findAllBookingByAmbulanceAndProgress(Long.parseLong(ContantUtil.authDTO.getAccountId()));
    }

    private void setBookingActive(boolean isChecked) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
        locationDTO.setStatus(isChecked);

        LocationAPI locationAPI = new LocationAPI(HomeAmbulanceActivity.this);
        locationAPI.updateStatus(locationDTO);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeAmbulanceActivity.this);

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