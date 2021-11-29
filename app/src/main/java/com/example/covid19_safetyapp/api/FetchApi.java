package com.example.covid19_safetyapp.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19_safetyapp.interfaces.FetchApiCallback;

public class FetchApi {

    Context context;

    public FetchApi(Context context) {
        this.context = context;
    }

    public void fetchApi(String url, FetchApiCallback fetchApiCallback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> fetchApiCallback.onApiResult(true, response),
                error -> fetchApiCallback.onApiResult(false, error.getMessage()));

        queue.add(request);
    }

}
