package com.week8.williamcoleman.peoplemongo.Views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.week8.williamcoleman.peoplemongo.MainActivity;
import com.week8.williamcoleman.peoplemongo.Models.Auth;
import com.week8.williamcoleman.peoplemongo.Models.Users;
import com.week8.williamcoleman.peoplemongo.Network.RestClient;
import com.week8.williamcoleman.peoplemongo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by williamcoleman on 11/7/16.
 */

public class MapPageView extends RelativeLayout implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {
    private Context context;

    @Bind(R.id.mapV)
    MapView mapView;

    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private LatLng latLng;
    final Handler handler = new Handler();
    private Double monLat;
    private Double monLon;
    private String monName;
    private String monID;
    GoogleMap map;


    public MapPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        ((MainActivity) context).showMenuItem(true);

        mapView.onCreate(((MainActivity) getContext()).savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);


        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();

    }


    public void onMapReady(GoogleMap googleMap) {
       map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        try{
            map.setMyLocationEnabled(true);
        }catch(SecurityException s) {}
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        handler.post(locationCheck);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void checkForNearby() {
        final RestClient restClient = new RestClient();
        restClient.getApiService().nearby(100).enqueue(new Callback<Users[]>() {
            @Override
            public void onResponse(Call<Users[]> call, Response<Users[]> response) {
                if (response.isSuccessful()) {
                    for (Users users : response.body()) {
                        monLat = users.getLatitude();
                        monLon = users.getLongitude();
                        monName = users.getFullname();
                        monID = users.getMonID();
                        LatLng monLatLng = new LatLng(monLon, monLat);
                        MarkerOptions peopleMon = new MarkerOptions().position(monLatLng).snippet(monID).title(monName);//icon
                        map.addMarker(peopleMon);
                    }
                }
            }

            @Override
            public void onFailure(Call<Users[]> call, Throwable t) {
                Toast.makeText(context, R.string.nearby_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    Runnable locationCheck = new Runnable() {
        @Override
        public void run() {
            try {
                lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                if (lastLocation != null) {
                    createLocationRequest();
                }
            } catch (SecurityException s) {
               s.printStackTrace();
            } finally {
                handler.postDelayed(this, 2000);
            }
        }
    };


        protected void createLocationRequest() {
            final LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            MarkerOptions options = new MarkerOptions().position(latLng).title("Current Location");
            map.addMarker(options);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            checkForNearby();

            Auth auth = new Auth(lastLocation.getLatitude(), lastLocation.getLongitude());
            RestClient restClient = new RestClient();
            restClient.getApiService().checkIn(auth).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("$$$$$$$$", latLng.toString());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Check In Failed", Toast.LENGTH_LONG).show();
                }
            });
        }


    }