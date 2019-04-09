package com.example.miagemto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miagemto.MetroDonnées.DescriptionLigne.Ligne;
import com.example.miagemto.MetroDonnées.HorairesLigne.LigneTram;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private ProgressDialog pDialog;

    RequestQueue mQueueSchedule;

    public static final String EXTRA_ID = "id";
    private GridView list_lines;
    private List<Ligne> listLignes = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mQueueSchedule = Volley.newRequestQueue(getContext());

        pDialog = new ProgressDialog(this.getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        getMetroLines();

    }

    private void getMetroLines() {
        showpDialog();
        if (listLignes.size() != 0){
            configureListView();
            hidepDialog();
            return;
        }
        String urlRequest = getString(R.string.metro_api) + "routers/default/index/routes";

        JsonArrayRequest linesRequest =
                new JsonArrayRequest(Request.Method.GET, urlRequest, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Ligne current = new Ligne(response.getJSONObject(i));
                                if (current.getType().equals("TRAM")
                                        || current.getType().equals("CHRONO")
                                        || current.getType().equals("PROXIMO"))
                                    listLignes.add(current);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        configureListView();
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ScheduleFragment.this.getContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                        hidepDialog();
                    }
                });

        mQueueSchedule.add(linesRequest);
    }

    private void configureListView(){
        list_lines = getActivity().findViewById(R.id.list_lines);
        LinesAdapter linesAdapter = new LinesAdapter();
        list_lines.setAdapter(linesAdapter);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    class LinesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listLignes.size();
        }

        @Override
        public Object getItem(int position) {
            return listLignes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getActivity().getLayoutInflater().inflate(R.layout.schedule_list, null);
            Button scheduleItem = convertView.findViewById(R.id.schedule_item);
            final Ligne current = listLignes.get(position);
            scheduleItem.setText(current.getShortName());
            scheduleItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent lineStops = new Intent(getContext(), LineStops.class);
                    lineStops.putExtra(EXTRA_ID, current.getId());
                    startActivity(lineStops);
                }
            });
            return convertView;
        }
    }

}
