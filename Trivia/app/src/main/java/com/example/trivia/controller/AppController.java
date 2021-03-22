package com.example.trivia.controller;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    public static AppController mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized AppController getInstance() {
//        if (mInstance == null)
//            mInstance = new AppController();

        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
//        set the default tag if tag is empty
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequest(Object tag) {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(tag);
    }
}
