package com.example.miagemto;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miagemto.MetroDonnées.HorairesLigne.Arret;
import com.example.miagemto.MetroDonnées.HorairesLigne.LigneTram;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LineStops extends AppCompatActivity {

    private ProgressDialog pDialog;

    ListView list_stop, list_stop2;
    private String id;
    LigneTram ligneTram;
    private List<String> favoritesStops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_stops);
        setContentView(R.layout.activity_line_stops);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        id = getIntent().getStringExtra(ScheduleFragment.EXTRA_ID);

        getLinesSchedule(id);

    }

    private void configureListView(){

        TextView directionTitle0 = findViewById(R.id.title_direction);
        directionTitle0.setText(id + " Direction " + ligneTram.getDirection1().getArrets().get(0).getStopName());
        TextView directionTitle1 = findViewById(R.id.title_direction2);
        directionTitle1.setText(id + " Direction " + ligneTram.getDirection0().getArrets().get(0).getStopName());

        list_stop = findViewById(R.id.stop_list);
        list_stop2 = findViewById(R.id.stop_list2);
        ListStopsAdapter linesAdapter0 = new ListStopsAdapter(ligneTram.getDirection0().getArrets());
        ListStopsAdapter linesAdapter1 = new ListStopsAdapter(ligneTram.getDirection1().getArrets());
        list_stop.setAdapter(linesAdapter0);
        list_stop2.setAdapter(linesAdapter1);
    }

    public void getLinesSchedule(String name){
        showpDialog();
        String sURL = getString(R.string.metro_api)+"ficheHoraires/json?route="+name+ "&time=" + System.currentTimeMillis();;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, sURL, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        ligneTram = new LigneTram(response);
                        configureListView();
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });


        MetroController.getInstance().addToRequestQueue(request, this);
    }

    class ListStopsAdapter extends BaseAdapter {

        List<Arret> stops = new ArrayList<>();

        public ListStopsAdapter(List<Arret> stops){
            this.stops = stops;
        }
        @Override
        public int getCount() {
            return stops.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.line_stop_item, null);
            TextView scheduleItem = convertView.findViewById(R.id.name_stop);
            final Arret current = stops.get(position);
            scheduleItem.setText(current.getStopName());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View detailView = inflater.inflate(R.layout.line_stops_horaires, null);
                    final PopupWindow popupNextTrip = new PopupWindow(
                            detailView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

                    final FloatingActionButton addFavorite = detailView.findViewById(R.id.add_favoris);
                    addFavorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(favoritesStops.contains(current.getStopName())){
                                favoritesStops.remove(current.getStopName());
                                addFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_24dp));
                            }else{
                                favoritesStops.add(current.getStopName());
                                addFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_24dp));
                            }
                        }
                    });

                    Button close, refresh;
                    close = detailView.findViewById(R.id.button_popup_close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupNextTrip.dismiss();
                        }
                    });

                    final TextView popupText = detailView.findViewById(R.id.stop_detail);
                    popupText.setText(getTripConverted(current.getTrip(), current.getStopName()));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        popupNextTrip.setElevation(5.0f);
                    }

                    RelativeLayout my_rootLayout = findViewById(R.id.line_stop_view_root);
                    popupNextTrip.setOutsideTouchable(true);
                    popupNextTrip.showAtLocation(my_rootLayout, Gravity.CENTER, 0, 0);
                    if(favoritesStops.contains(current.getStopName())){
                        addFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_24dp));
                    }

                    refresh = detailView.findViewById(R.id.button_popup_refresh);
                    refresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getLinesSchedule(id);
                            popupText.setText(getTripConverted(current.getTrip(), current.getStopName()));
                        }
                    });

                }
            });

            return convertView;
        }
    }

    private String getTripConverted(List<String> trips, String stopName){
        final StringBuffer tripConverted = new StringBuffer( stopName + ":\n");
        for (int i=0; i< trips.size(); i++){
            try{
                int seconds = Integer.parseInt(trips.get(i));
                tripConverted.append(((seconds / 3600)+2)%24 +"H " +
                        ((seconds %3600) / 60 + "mins\n"));
            }catch (Exception e){

            }
        }
        return tripConverted.toString();
    }

    private void showpDialog() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
