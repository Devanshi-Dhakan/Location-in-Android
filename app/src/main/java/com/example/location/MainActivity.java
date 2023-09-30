package com.example.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.*;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.pm.PackageManager.*;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private Button btngetlocation;
    private TextView txtlocation;
    private final int Gpsrequest=100;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btngetlocation=(Button)findViewById(R.id.button);
        txtlocation=(TextView)findViewById(R.id.textView);

        btngetlocation.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                                          && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                                      ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Gpsrequest);
                                                  } else {
                                                      getlocation();
                                                  }
                                              }
                                          });

            }


            public void onRequestPermissionResult(int requestcode, @NonNull String[] permissions,@NonNull int[] grantresult)
            {
                switch (requestcode)
                {
                    case Gpsrequest:{
                        if (grantresult.length>0 && grantresult[0]== PERMISSION_GRANTED)
                        {
                            getlocation();
                        }
                    }
                }
                super.onRequestPermissionsResult(requestcode,permissions,grantresult);
            }


    private void getlocation() {
        try {
            locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        txtlocation.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude());

        Locationhelper help=new Locationhelper(

                location.getLatitude(),
                location.getLongitude()
        );

        FirebaseDatabase.getInstance().getReference("current Location")
                .setValue(help).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Location saved", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "location not saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
