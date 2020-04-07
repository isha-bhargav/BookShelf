package com.example.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Main7Activity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    TextView addV;
    RadioGroup radio;
    EditText adde;
     String ad,a;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        adde=findViewById(R.id.ed1);
        radio=findViewById(R.id.radioGroup);
        addV=findViewById(R.id.txt1);
        Button getloc=findViewById(R.id.btn0);
        ad=adde.getText().toString();
        Log.i("log",ad);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Main7Activity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Main7Activity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        new AlertDialog.Builder(Main7Activity.this).setTitle("Permission Required")
                                .setMessage("Please allow to access the feature")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ActivityCompat.requestPermissions(Main7Activity.this,
                                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(Main7Activity.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(Main7Activity.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        // Logic to handle location object
                                        Double lat=location.getLatitude();
                                        Double lon=location.getLongitude();
                                        Geocoder geocoder;
                                        List<Address> addresses;
                                        geocoder = new Geocoder(Main7Activity.this, Locale.getDefault());

                                        try {
                                            addresses = geocoder.getFromLocation(lat, lon, 1);
                                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                            String city = addresses.get(0).getLocality();
                                            String state = addresses.get(0).getAdminArea();
                                            String country = addresses.get(0).getCountryName();
                                            String postalCode = addresses.get(0).getPostalCode();
                                            String knownName = addresses.get(0).getFeatureName();
                                                    // Only if available else return NULL
                                            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                            a=address+" ,"+city+" ,"+country+" ,"+postalCode+" ,"+knownName;
                                            adde.setText(address+" ,"+city+" ,"+country+" ,"+postalCode+" ,"+knownName);
                                            addV.setVisibility(View.GONE);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                         }
                                }
                            });
                }
            }
        });
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radio.getCheckedRadioButtonId() == R.id.btn1) {
                    new AlertDialog.Builder(Main7Activity.this).setTitle("Confirmation")
                            .setMessage("Do you want to continue")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (TextUtils.isEmpty(adde.getText().toString().trim())) {
                                        Toast.makeText(Main7Activity.this, "Please enter address", Toast.LENGTH_SHORT).show();
                                        addV.setVisibility(View.VISIBLE);

                                    } else {
                                        Toast.makeText(Main7Activity.this, "Your order has" +
                                                " been confirmed and will be delivered at " + adde.getText(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();

                } else if (radio.getCheckedRadioButtonId() == R.id.btn2) {
                    new AlertDialog.Builder(Main7Activity.this).setTitle("Confirmation")
                            .setMessage("Do you want to continue")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (TextUtils.isEmpty(adde.getText().toString().trim())) {
                                      //  Toast.makeText(Main7Activity.this, "Please enter address", Toast.LENGTH_SHORT).show();
                                        addV.setVisibility(View.VISIBLE);

                                    } else {
                                        addV.setVisibility(View.GONE);
                                        Toast.makeText(Main7Activity.this, "Your order has" +
                                                " been confirmed and will be delivered at " + adde.getText(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Main7Activity.this, Main5Activity.class));
                                    }
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
                }
            }
        });
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        {
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {

            }
        }
    }
}
