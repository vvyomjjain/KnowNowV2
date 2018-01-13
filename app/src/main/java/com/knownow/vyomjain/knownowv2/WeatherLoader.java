package com.knownow.vyomjain.knownowv2;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

/**
 * Created by Vyom Jain on 09-09-2017.
 */

public class WeatherLoader extends AsyncTaskLoader<WeatherObject> {

    private static final String LOG_TAG = WeatherLoader.class.getName(); //for error logs
    private String weatherUrl; //the url

    public WeatherLoader(Context context, String weatherUrl){
        super(context);
        this.weatherUrl = weatherUrl;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: onStartLoading() is called");
        forceLoad();
    }

    @Override
    public WeatherObject loadInBackground() {
        Log.i(LOG_TAG, "TEST: loadInBackground() is called");

        if (weatherUrl == null){
            return null;
        }

        WeatherObject currentWeather = QueryUtils.fetchWeather(weatherUrl);
        return currentWeather;
    }
}
