package me.akhilarimbra.bootcamplocator.model;

/**
 * Created by akhilraj on 10/12/16.
 */

public class Devslopes {
    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationImageUrl() {
        return locationImageUrl;
    }

    private float longitude;
    private float latitude;
    private String locationTitle;
    private String locationAddress;
    private String locationImageUrl;

    public Devslopes(
            float latitude,
            float longitude,
            String locationTitle,
            String locationAddress,
            String locationImageUrl) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationTitle = locationTitle;
        this.locationAddress = locationAddress;
        this.locationImageUrl = locationImageUrl;
    }
}
