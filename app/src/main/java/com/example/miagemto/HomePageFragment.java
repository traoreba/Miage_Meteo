package com.example.miagemto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miagemto.MetroDonnées.LignesProximite.LignesProximite;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment implements LocationListener {

    ImageView weather_icon;
    TextView weather_city, weather_temperature;
    RequestQueue mQueueWeather;

    private static String TAG = HomePageFragment.class.getSimpleName();
    private ProgressDialog pDialog;

    MapView map_view;
    IMapController mapController;

    private MainActivity parentActivity;

    Button refresh_position;
    Marker myPosition;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 10;
    private LocationManager locationManager;
    private String locationProvider = LocationManager.NETWORK_PROVIDER;
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static final int THIRTY_METERS = 300;
    private Location currentBestLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        checkPermissionLocation();
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        weather_city = getActivity().findViewById(R.id.weather_city);
        weather_icon = getActivity().findViewById(R.id.weather_icon);
        weather_temperature = getActivity().findViewById(R.id.weather_temperature);
        mQueueWeather = Volley.newRequestQueue(getContext());

        pDialog = new ProgressDialog(this.getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        map_view = getActivity().findViewById(R.id.map_view);
        mapController = map_view.getController();
        if(currentBestLocation !=null) {
            GeoPoint startPoint = new GeoPoint(currentBestLocation.getLatitude(), currentBestLocation.getLongitude());
            mapController.setZoom(18.0);
            mapController.setCenter(startPoint);
            putMarkersOnMap(startPoint, "Ma position", getResources().getDrawable(R.drawable.ic_my_location_black_24dp), true);
            refresh_weather();

            getProximityStations();

        }
        refresh_position = getActivity().findViewById(R.id.refresh_map);
        refresh_position.setOnClickListener(
                new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        showpDialog();
                        refresh_weather();
                        refresh_map_view();
                        hidepDialog();
                    }
                }
        );
    }

    public boolean checkPermissionLocation (){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this.getContext())
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(HomePageFragment.this.getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION
                                        );
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            locationManager.requestLocationUpdates(locationProvider, TWO_MINUTES, THIRTY_METERS, this);
            currentBestLocation = locationManager.getLastKnownLocation(locationProvider);
            return true;
        }
    }

    private void putMarkersOnMap(GeoPoint geoPoint, String description, Drawable icon, boolean isMyPosition){
        Marker m = new Marker(map_view);
        m.setTextLabelBackgroundColor(R.color.textPrimary);
        m.setTextLabelFontSize(R.dimen.text_max);
        m.setTextLabelForegroundColor(R.color.textIcon);
        m.setTitle(description);
        //must set the icon to null last
        m.setIcon(icon);
        m.setPosition(new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude()));
        if(isMyPosition) {
            map_view.getOverlays().remove(myPosition);
        }
        map_view.getOverlays().add(m);
    }

    private void refresh_map_view() {
        GeoPoint startPoint = new GeoPoint(currentBestLocation.getLatitude(),currentBestLocation.getLongitude());
        mapController.setCenter(startPoint);
        getProximityStations();
    }

    public void refresh_weather() {
        //RequestQueue queue = Volley.newRequestQueue();

        String requestURL = getString(R.string.weather_api)
                + "?lat=" + currentBestLocation.getLatitude()
                + "&lon=" + currentBestLocation.getLongitude()
                + "&units=metric&lang=fr"
                + "&appid=" + getString(R.string.api_id);

        JsonObjectRequest weather_request =  new JsonObjectRequest(Request.Method.GET, requestURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject weather = (JSONObject) response.getJSONArray("weather").get(0);
                            JSONObject main = response.getJSONObject("main");
                            weather_city.setText(response.getString("name"));
                            Picasso.get().load(getString(R.string.weater_icon)
                                    + weather.getString("icon")+".png").into(weather_icon);

                            weather_icon.setScaleType(ImageView.ScaleType.FIT_XY);
                            weather_temperature.setText((int)main.getDouble("temp") + "°\n"
                                    +(int)main.getDouble("temp_min")+ "° /"
                                    +(int)main.getDouble("temp_max")+"°\n");
                            weather_temperature.append(weather.getString("description"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        mQueueWeather.add(weather_request);
    }

    private void getProximityStations() {
        showpDialog();

        String sURL = getString(R.string.metro_api) + "linesNear/json?x=" + currentBestLocation.getLongitude() + "&y=" + currentBestLocation.getLatitude() + "&dist=700&details=false";

        // RequestQueue queue = Volley.newRequestQueue(this.getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, sURL, null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            ArrayList<GeoPoint> nearStations = new ArrayList<GeoPoint>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject ligne_proximite = (JSONObject) response.get(i);
                                nearStations.add(new GeoPoint(ligne_proximite.getDouble("lat"),ligne_proximite.getDouble("lon")));
                                putMarkersOnMap(new GeoPoint(ligne_proximite.getDouble("lat"),ligne_proximite.getDouble("lon")),
                                        ligne_proximite.getString("name") + "\nPochains passages\n"
                                                + ligne_proximite.getJSONArray("lines").toString(),
                                        getResources().getDrawable(R.drawable.ic_place_24dp),
                                        true);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HomePageFragment.this.getContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(HomePageFragment.this.getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        mQueueWeather.add(request);
        hidepDialog();
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    /** Determines whether one GPSTracker reading is better than the current GPSTracker fix
     * @param location  The new GPSTracker that you want to evaluate
     */
    protected boolean isBetterLocation(Location location) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (isBetterLocation(location)){
            currentBestLocation = location;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        checkPermissionLocation();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  MainActivity){
            parentActivity = (MainActivity) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        map_view.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map_view.onPause();
    }

}
