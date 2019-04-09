package com.example.miagemto;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
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

import java.util.List;

public class LineStops extends AppCompatActivity {

    private ProgressDialog pDialog;

    ListView list_stop;
    RequestQueue mQueueQueue;
    LigneTram ligneTram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_stops);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        mQueueQueue = Volley.newRequestQueue(this);

        String id = getIntent().getStringExtra(ScheduleFragment.EXTRA_ID);

        getLinesSchedule(id);
        /*PopupWindow popup_line_list = new PopupWindow(this);
        popup_line_list.showAtLocation((View) getResources().getLayout(R.layout.line_stops_horaires), Gravity.CENTER, 240, 240);*/
    }

    private void configureListView(){
        list_stop = findViewById(R.id.stop_list);
        ListStopsAdapter linesAdapter = new ListStopsAdapter();
        list_stop.setAdapter(linesAdapter);
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


        mQueueQueue.add(request);
        // Adding request to request queue
        //MetroController.getInstance().addToRequestQueue(request, this.getContext());
    }

    class ListStopsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ligneTram.getDirection0().getArrets().size();
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
            final Arret current = ligneTram.getDirection0().getArrets().get(position);
            scheduleItem.setText(current.getStopName());
            List<String> trips = current.getTrip();
            final StringBuffer tripConverted = new StringBuffer("Prochains Passages: \n");
            for (int i=0; i< trips.size(); i++){
                try{
                    int seconds = Integer.parseInt(trips.get(i));
                    tripConverted.append(seconds/(60*60) +"H " +
                            (seconds%(60*60))/60 + "Mins\n");
                }catch (Exception e){

                }
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View detailView = inflater.inflate(R.layout.line_stops_horaires, null);
                    final PopupWindow nextTrip = new PopupWindow(
                            detailView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

                    Button addFavorite, close, refresh;
                    addFavorite = detailView.findViewById(R.id.add_favoris);
                    close = detailView.findViewById(R.id.button_popup_close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nextTrip.dismiss();
                        }
                    });
                    refresh = detailView.findViewById(R.id.button_popup_refresh);

                    TextView popupText = detailView.findViewById(R.id.stop_detail);
                    popupText.setText(tripConverted);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        nextTrip.setElevation(5.0f);
                    }

                    ConstraintLayout my_rootLayout = findViewById(R.id.line_stop_view_root);
                    nextTrip.showAtLocation(my_rootLayout, Gravity.CENTER, 0, 0);

                }
            });

            return convertView;
        }
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
