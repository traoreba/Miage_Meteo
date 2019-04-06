package com.example.miagemto;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class HomePageFragment extends Fragment {

    ImageView weather_icon;
    TextView weather_city, weather_temperature;
    RequestQueue mQueueWeather;

    private static String TAG = HomePageFragment.class.getSimpleName();
    private ProgressDialog pDialog;

    MapView map_view;
    IMapController mapController;

    private MainActivity parentActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
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
        GeoPoint startPoint = new GeoPoint(parentActivity.currentBestLocation.getLatitude(), parentActivity.currentBestLocation.getLongitude());
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        refresh_weather();

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

    public void refresh_weather() {

        //RequestQueue queue = Volley.newRequestQueue();

        String requestURL = getString(R.string.weather_api)
                + "?lat=" + parentActivity.currentBestLocation.getLatitude()
                + "&lon=" + parentActivity.currentBestLocation.getLongitude()
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


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  MainActivity){
            parentActivity = (MainActivity) context;
        }
    }
}
