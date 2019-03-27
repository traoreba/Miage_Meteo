package com.example.miagemto;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MetroController {

    public static final String TAG = MetroController.class.getSimpleName();

    private RequestQueue metroRequestQueue;

    private static MetroController metroInstance;

    public static synchronized MetroController getInstance() {
        if (metroInstance == null) {
            metroInstance = new MetroController();
        }
        return metroInstance;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (metroRequestQueue == null) {
            metroRequestQueue = Volley.newRequestQueue(context);
        }
        return metroRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag, Context context) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue(context).add(req);
    }

    public <T> void addToRequestQueue(Request<T> req, Context context) {
        req.setTag(TAG);
        getRequestQueue(context).add(req);
    }


}
