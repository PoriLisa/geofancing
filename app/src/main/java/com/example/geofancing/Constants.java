package com.example.geofancing;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class Constants {
    public static final String GEOFENCE_REQ_ID = "My Geofence";
    public static final String GEOFENCE_ID = "FENCH";
    public static final float GEOFENCE_RADIUS_IN_METERS = 4000;

    /**
     * Map for storing information about tacme in the dubai.
     */
    public static final HashMap<String, LatLng> AREA_LANDMARKS = new HashMap<String, LatLng>();

    static {
        // Tacme
        AREA_LANDMARKS.put(GEOFENCE_ID, new LatLng(20.513550, 86.399219));

        //20.295695, 85.830884
        //20.513554, 86.399219
    }
}
