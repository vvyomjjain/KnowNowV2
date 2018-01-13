package com.knownow.vyomjain.knownowv2;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Vyom Jain on 11-11-2017.
 */

public class WeatherForecastLoader extends AsyncTaskLoader<List<WeatherForecastObject>> {

    private static final String LOG_TAG = WeatherForecastLoader.class.getName();
    private String url;

    public WeatherForecastLoader(Context context, String url){
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading(){
        Log.i(LOG_TAG, "TEST: onStartLoading called.");
        forceLoad();
    }

    @Override
    public List<WeatherForecastObject> loadInBackground(){
        Log.i(LOG_TAG, "TEST: loadInBackground called.");
        if(url == null){
            return null;
        }

        List<WeatherForecastObject> weatherForecastObjectList = QueryUtils.fetchForecast(url);
        return weatherForecastObjectList;
    }
}
