package com.example.miagemto;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.miagemto.MetroDonnées.LignesProximite.LignesProximite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MetroFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_metro, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
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
    }

    private static String TAG = MetroFragment.class.getSimpleName();
    private Button horairesLignes, ligneAProximite, DescriptionLigne;
    private LignesProximite ligneProximo;
    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;

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
                            Toast.makeText(MetroFragment.this.getContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(MetroFragment.this.getContext(),
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
