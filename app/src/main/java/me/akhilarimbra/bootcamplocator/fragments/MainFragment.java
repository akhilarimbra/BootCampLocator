package me.akhilarimbra.bootcamplocator.fragments;


import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.akhilarimbra.bootcamplocator.R;
import me.akhilarimbra.bootcamplocator.model.Devslopes;
import me.akhilarimbra.bootcamplocator.services.DataService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions userMarker;
    private LocationsListFragment listFragment;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listFragment = (LocationsListFragment) getActivity()
                        .getSupportFragmentManager()
                        .findFragmentById(R.id.container_location_list);


        if (listFragment == null) {
            listFragment = LocationsListFragment.newInstance();
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction().add(R.id.container_location_list, listFragment)
                    .commit();
        }

        final EditText zipCodeEditText = (EditText) view.findViewById(R.id.zip_code_edit_text);
        zipCodeEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Validation is not done
                    String text = zipCodeEditText.getText().toString();
                    int zip = Integer.parseInt(text);
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(zipCodeEditText.getWindowToken(), 0);

                    showList();
                    updateMapForZipCode(zip);
                    return true;
                }

                return false;
            }
        });

        final ImageButton zipCodeSearchButton = (ImageButton) view.findViewById(R.id.zip_code_search_button);

        zipCodeSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = zipCodeEditText.getText().toString();
                int zip = Integer.parseInt(text);
                updateMapForZipCode(zip);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(32.776664f, -96.796988f), 15));
            }
        });

        hideList();
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateMapForZipCode(92284);
    }

    public void setUserMarkers(LatLng latLng) {
        if (userMarker == null) {
            userMarker = new MarkerOptions().position(latLng).title("Current Location");
            mMap.addMarker(userMarker);
            Log.v("Test Log", "New Latitude : " + latLng.latitude + ", New Longitude : " + latLng.longitude);
        }

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<android.location.Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            int zip = Integer.parseInt(addresses.get(0).getPostalCode());
            updateMapForZipCode(zip);
        } catch (IOException exception) {

        }

        updateMapForZipCode(92284);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    public void updateMapForZipCode(int zipCode) {
        ArrayList<Devslopes> locations = DataService.getInstance().getBootCampLocationsWithInTenMilesOfZip(zipCode);

        for (int i = 0; i < locations.size(); i++) {
            Devslopes loc = locations.get(i);
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude()));
            markerOptions.title(loc.getLocationTitle());
            markerOptions.snippet(loc.getLocationAddress());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
            mMap.addMarker(markerOptions);
        }
    }

    private void hideList() {
        getActivity().getSupportFragmentManager().beginTransaction().hide(listFragment).commit();
    }

    private void showList() {
        getActivity().getSupportFragmentManager().beginTransaction().show(listFragment).commit();
    }

}
