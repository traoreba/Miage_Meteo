package com.example.miagemto;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Text;

public class HomePageFragment extends Fragment {

    SwipeRefreshLayout swipe_lnes_list;
    ListView lines_list_view;
    int n = 0;
    String result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        swipe_lnes_list = getActivity().findViewById(R.id.swipe_lines_list);
        lines_list_view = getActivity().findViewById(R.id.list_lines);
        final TextView test = getActivity().findViewById(R.id.test);

        swipe_lnes_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                n++;
                callWebService();
                test.setText(result+ n);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe_lnes_list.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void callWebService() {
        String url = "http://data.metromobilite.fr/api/routers/default/index/routes";

        //RequestQueue queue = Volley.newRequestQueue();

        StringRequest request =  new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println( response);
                        result = response;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
}
