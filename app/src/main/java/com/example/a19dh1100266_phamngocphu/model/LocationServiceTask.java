package com.example.a19dh1100266_phamngocphu.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.GeoPoint;


import java.io.IOException;
import java.util.List;

public class LocationServiceTask {

    private static final String LOG_TAG = "ITOPreconditions";

    public static boolean isLocationServiceEnabled(FragmentActivity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //I have read this is not actually required on all devices, but I have not found a way
            //to check if it is required.
            //If location is not enabled the BLE scan fails silently (scan callback is never called)
            if (!locationManager.isLocationEnabled()) {
                Log.i(LOG_TAG, "Location not enabled (API>=P check)");
                return false;
            }
        } else {
            //Not sure if this is the correct check, gps is not really required, but passive provider
            //does not seem to be enough
            if (!locationManager.getProviders(true).contains(LocationManager.GPS_PROVIDER)) {
                Log.i(LOG_TAG, "Location not enabled (API<P check)");
                return false;
            }
        }
        return true;

    }

    public static void displayEnableLocationServiceDialog(FragmentActivity activity) {
    }

    public static LatLng getLatLngFromAddress(Context context, String address) {
        Geocoder geocoder=new Geocoder(context);
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocationName(address, 5);
            if(addressList!=null){
                Address singleaddress=addressList.get(0);
                LatLng latLng=new LatLng(singleaddress.getLatitude(),singleaddress.getLongitude());
                return latLng;
            }
            else{
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
