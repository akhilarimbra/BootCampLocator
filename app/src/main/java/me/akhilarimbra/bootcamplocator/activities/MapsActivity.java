package me.akhilarimbra.bootcamplocator.activities;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import me.akhilarimbra.bootcamplocator.R;
import me.akhilarimbra.bootcamplocator.fragments.MainFragment;

public class MapsActivity extends FragmentActivity
        implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener {

    private GoogleApiClient mGoogleApiclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mGoogleApiclient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
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

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

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

    public void startLocationServices() {
        Log.v("GoogleApiClient", "Starting Location Services Called");

        try {
            LocationRequest req = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiclient, req, this);
        } catch (SecurityException exception) {
            //Show dialog to the user stating that we can't get location unless they give app permissions for tracing user location
            Log.v("GoogleApiClient", exception.toString());
            Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
