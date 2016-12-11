package me.akhilarimbra.bootcamplocator.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.akhilarimbra.bootcamplocator.R;
import me.akhilarimbra.bootcamplocator.model.Devslopes;

/**
 * Created by akhilraj on 10/12/16.
 */

public class LocationViewHolder extends RecyclerView.ViewHolder {

    private ImageView locationImageView;
    private TextView locationTitleTextView;
    private TextView locationAddressTextView;

    public LocationViewHolder(View itemView) {
        super(itemView);

        locationImageView = (ImageView) itemView.findViewById(R.id.location_image_view);
        locationTitleTextView = (TextView) itemView.findViewById(R.id.location_title_text);
        locationAddressTextView = (TextView) itemView.findViewById(R.id.location_address_text);
    }

    public void updateUI(Devslopes location) {
        String uri = location.getImageUrl();
        int resource = locationImageView.getResources().getIdentifier(uri, null, locationImageView.getContext().getPackageName());
        locationImageView.setImageResource(resource);
        locationTitleTextView.setText(location.getLocationTitle());
        locationAddressTextView.setText(location.getLocationAddress());
    }
}
