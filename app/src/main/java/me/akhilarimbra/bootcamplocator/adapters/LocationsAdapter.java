package me.akhilarimbra.bootcamplocator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.akhilarimbra.bootcamplocator.R;
import me.akhilarimbra.bootcamplocator.holders.LocationViewHolder;
import me.akhilarimbra.bootcamplocator.model.Devslopes;

/**
 * Created by akhilraj on 10/12/16.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    private ArrayList<Devslopes> locations;

    public LocationsAdapter(ArrayList<Devslopes> locations) {
        this.locations = locations;
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        final Devslopes location = locations.get(position);
        holder.updateUI(location);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Load Details Page

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);
        return new LocationViewHolder(card);
    }
}
