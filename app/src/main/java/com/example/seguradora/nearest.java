package com.example.seguradora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

public class nearest extends AppCompatActivity {
    public final static String EXTRA_MESSAGE_LOCAL_NAME = "com.example.seguradora.name";
    public final static String EXTRA_MESSAGE_LATITUDE = "com.example.seguradora.latitude";
    public final static String EXTRA_MESSAGE_LONGITUDE = "com.example.seguradora.longitude";


    private static final String TAG = "nearest";
    int LOCATION_REQUEST_CODE = 1001;
    String nothLocal = "R. Paulo Henrique Machado Pímentel, 125 - Inácio Barbosa, Aracaju - SE, 49040-74";
    String southLocal = "R. Piauí, 431 - Santa Maria Goretti, Porto Alegre - RS, 91030-320";
    String southWestLocal = "Av. dos Autonomistas, 741 - Vila Yara, Osasco - SP, 06090-020";

    double lat,log;

    TextView txtActualPostion, txtNearsertPostion;
    Button btnMap,btnSave;

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for(Location location: locationResult.getLocations()){
                //Localização aqui
                try {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                    Geocoder geocoder = new Geocoder(nearest.this,
                            Locale.getDefault());
                    List<Address> addresses = null;

                    addresses = geocoder.getFromLocation(latitude,longitude,1);

                    String address = addresses.get(0).getAddressLine(0);

                    txtActualPostion.setText(address);

                    nearestPlace(latitude);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void nearestPlace(double latitude) {
        if(latitude >= -14) {
            txtNearsertPostion.setText(nothLocal);
            double latNear = -10.9511943;
            double logNear = -37.0745493;

            lat = latNear;
            log = logNear;
        } else if(latitude <= -27.30) {
            txtNearsertPostion.setText(southLocal);
            double latNear = -10.9511943;
            double logNear = -51.1768878;

            lat = latNear;
            log = logNear;
        }
        else{
            txtNearsertPostion.setText(southWestLocal);
            double latNear = -23.5462602;
            double logNear = -46.7635113;

            lat = latNear;
            log = logNear;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest);

        txtActualPostion = (TextView) findViewById(R.id.txtYourLocation);
        txtNearsertPostion = (TextView) findViewById(R.id.txtNearestLocaation);

        btnMap = (Button) findViewById(R.id.btnMap);
        btnSave = (Button) findViewById(R.id.button4) ;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nearest.this,MapsActivity.class);

                String localName = txtNearsertPostion.getText().toString();


                intent.putExtra(EXTRA_MESSAGE_LATITUDE, lat);
                intent.putExtra(EXTRA_MESSAGE_LONGITUDE,log);
                intent.putExtra(EXTRA_MESSAGE_LOCAL_NAME,localName);

                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createFile(southLocal);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.
                PERMISSION_GRANTED) {
            checkSettingsAndStartLocationUpdates();
        } else {
            askLocationPermission();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);

        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });

        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(nearest.this, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(nearest.this, "Aceite as permissões para o app funcionar",
                        Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkSettingsAndStartLocationUpdates();
            } else {

            }
        }
    }

    public void createFile (String name) throws FileNotFoundException {
        File file = new File(this.getFilesDir(), "filename");
        String filename = "myfile";
        String fileContents = name;

        FileInputStream fis = this.openFileInput("asfd");
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            String contents = stringBuilder.toString();
        }
    }
}