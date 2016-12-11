package me.akhilarimbra.bootcamplocator.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.akhilarimbra.bootcamplocator.R;
import me.akhilarimbra.bootcamplocator.adapters.LocationsAdapter;
import me.akhilarimbra.bootcamplocator.services.DataService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationsListFragment extends Fragment {

    public LocationsListFragment() {
        // Required empty public constructor
    }

    public static LocationsListFragment newInstance() {
        LocationsListFragment fragment = new LocationsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_locations);
        recyclerView.setHasFixedSize(true);

        LocationsAdapter locationsAdapter = new LocationsAdapter(
                DataService.getInstance()
                .getBootCampLocationsWithInTenMilesOfZip(92284));
        recyclerView.setAdapter(locationsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);


        return view;
    }

}
