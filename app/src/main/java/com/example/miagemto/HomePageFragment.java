package com.example.miagemto;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Bundle;
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

public class HomePageFragment extends Fragment {

    SwipeRefreshLayout swipe_refresh;

    ImageView weather_icon;
    TextView weather_city, weather_temperature;
    RequestQueue mQueueWeather;

    private static String TAG = HomePageFragment.class.getSimpleName();
    private Button horairesLignes, ligneAProximite, DescriptionLigne;
    private LignesProximite ligneProximo;
    // Progress dialog
    private ProgressDialog pDialog;
    private TextView txtResponse;
    // temporary string to show the parsed response
    private String jsonResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        weather_city = getActivity().findViewById(R.id.weather_city);
        weather_icon = getActivity().findViewById(R.id.weather_icon);
        weather_temperature = getActivity().findViewById(R.id.weather_temperature);
        mQueueWeather = Volley.newRequestQueue(getContext());

        swipe_refresh = getActivity().findViewById(R.id.swipe_refresh);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_weather();
                callWebservice();
            }
        });

        horairesLignes = getActivity().findViewById(R.id.lineTime);
        ligneAProximite = getActivity().findViewById(R.id.ligneProximite);
        txtResponse = getActivity().findViewById(R.id.txtResponse);

        pDialog = new ProgressDialog(this.getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        ligneAProximite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                callWebservice();
            }
        });

        refresh_weather();


    }

    public void refresh_weather() {

        //RequestQueue queue = Volley.newRequestQueue();

        JsonObjectRequest weather_request =  new JsonObjectRequest(Request.Method.GET, getString(R.string.weather_api),null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject city_info = response.getJSONObject("city_info");
                            JSONObject current_condition = response.getJSONObject("current_condition");
                            JSONObject fcst_day_0 = response.getJSONObject("fcst_day_0");
                            weather_city.setText(city_info.getString("name"));
                            Picasso.get().load(current_condition.getString("icon")).into(weather_icon);
                            weather_temperature.setText(current_condition.getString("tmp") + "°\n"
                                    +fcst_day_0.getString("tmin")+ "° /"
                                    +fcst_day_0.getString("tmax")+"°\n");
                            weather_temperature.append(current_condition.getString("condition"));
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
        swipe_refresh.setRefreshing(false);
    }

    private void callWebservice () {
        String sURL = "http://data.metromobilite.fr/api/linesNear/json?x=5.709360123&y=45.176494599999984&dist=400&details=true";
        showpDialog();

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        JsonArrayRequest req = new JsonArrayRequest(sURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {

                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject ligneProximo = (JSONObject) response
                                        .get(i);




                                String id = ligneProximo.getString("id");
                                String name = ligneProximo.getString("name");
                                String lon = ligneProximo.getString("lon");
                                String lat = ligneProximo.getString("lat");
                                String lines = ligneProximo.getString("lines");
                                // JSONObject lines = ligneProximite.getJSONObject("lines");
                                //String lon = lines.getString("lat");
                                // String lat = lines.getString("mobile");

                                jsonResponse += "id: " + id + "\n\n";
                                jsonResponse += "name: " + name + "\n\n";
                                jsonResponse += "lon: " + lon + "\n\n";
                                jsonResponse += "lat: " + lat + "\n\n\n";
                                jsonResponse += "lat: " + lines + "\n\n\n";

                            }

                            txtResponse.setText(jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HomePageFragment.this.getContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
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

        // Adding request to request queue
        MetroController.getInstance().addToRequestQueue(req, this.getContext());


    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
