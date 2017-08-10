package com.computerprogramming.toplatestnews;

import android.app.Application;
import android.util.Log;

import com.computerprogramming.toplatestnews.api.WebAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Skbhati on 03-08-2017.
 */

public class NewsApp extends Application {

    private static final String HTTPS_URL = "https://newsapi.org/";
    private static WebAPI mWebAPI;

    @Override
    public void onCreate() {
        super.onCreate();

    }


    public static WebAPI newInstance(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();


        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(HTTPS_URL)
                .build();
        mWebAPI = retrofit.create(WebAPI.class);
        return  mWebAPI;
    }

}
