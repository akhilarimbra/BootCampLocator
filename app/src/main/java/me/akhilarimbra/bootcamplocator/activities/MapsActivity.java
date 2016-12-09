package me.akhilarimbra.bootcamplocator.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import me.akhilarimbra.bootcamplocator.R;
import me.akhilarimbra.bootcamplocator.fragments.MainFragment;

public class MapsActivity extends FragmentActivity
        implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener {

    final int PERMISSION_LOCATION_CHECK = 111;

    private GoogleApiClient mGoogleApiclient;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Log.v("Testing Log", "Test");

        mGoogleApiclient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container_main);

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_main, mainFragment)
                    .commit();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, this.PERMISSION_LOCATION_CHECK);
            Log.v("Testing Log", "Requesting permissions");
        } else {
            Toast.makeText(this, "User has already granted necessary permissions", Toast.LENGTH_SHORT).show();
            startLocationServices();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("Testing Log",
                "Longitude : " + location.getLongitude() + ", Latitude : " + location.getLatitude() );
        Toast.makeText(this,
                "Longitude : " + location.getLongitude() + ", Latitude : " + location.getLatitude(),
                Toast.LENGTH_SHORT).show();
        mainFragment.setUserMarkers(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    protected void onStart() {
        mGoogleApiclient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiclient.disconnect();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_LOCATION_CHECK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    startLocationServices();
                } else {
                    //Show a dialog saying something like, you just denied the application permission
                    Log.v("Testing Log", "User denied location permission");
                    Toast.makeText(this,
                            "Please grant location permission, otherwise the app won't work dummy",
                            Toast.LENGTH_SHORT).show();
                }
        }
    }



    public void startLocationServices() {
        Log.v("Testing Log", "Starting Location Services Called");

        try {
            LocationRequest req = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiclient, req, this);
        } catch (SecurityException exception) {
            //Show dialog to the user stating that we can't get location unless they give app permissions for tracing user location
            Log.v("Testing Log", exception.toString());
            Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
