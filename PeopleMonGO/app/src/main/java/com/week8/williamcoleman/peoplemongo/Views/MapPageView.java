package com.week8.williamcoleman.peoplemongo.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.week8.williamcoleman.peoplemongo.MainActivity;
import com.week8.williamcoleman.peoplemongo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by williamcoleman on 11/7/16.
 */

public class MapPageView extends RelativeLayout implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {
    private Context context;

    @Bind(R.id.mapV)
    MapView mapView;

    private GoogleMap map;
    Place currentPlace;
    Integer BUILDING_LEVEL = 20;

    public MapPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        mapView.onCreate(((MainActivity) getContext()).savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }


    public void onMapReady(GoogleMap googleMap) {
        int BUILDING_LEVEL = 20;
        LatLng MY_HOUSE = new LatLng(37.8145, -82.8071);

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MY_HOUSE, BUILDING_LEVEL));
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeball))
                .anchor(0.0f, 1.0f)
                .position(new LatLng(37.8145, -82.8071)));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPlace.getLatLng(), BUILDING_LEVEL));
//        map.addMarker(new MarkerOptions().position(currentPlace.getLatLng()));
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

}
