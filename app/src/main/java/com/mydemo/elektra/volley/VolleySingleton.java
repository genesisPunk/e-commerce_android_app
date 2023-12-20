package com.mydemo.elektra.volley;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mydemo.elektra.MyApplication;

public class VolleySingleton {

    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){
        //private constructor for preventing construction of new objects

        mRequestQueue = Volley.newRequestQueue(MyApplication.myAppContext());
    }


    public static VolleySingleton getInstance(){

        if(sInstance == null)
            return new VolleySingleton();
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}
