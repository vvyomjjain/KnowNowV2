package com.knownow.vyomjain.knownowv2;


import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.data;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeWeatherFragment extends Fragment implements LoaderManager.LoaderCallbacks<WeatherObject>{

    private static Date today = new Date();
    private static TextView weather;
    private static TextView weatherUnit;
    WeatherObject currentWeather = null;
    private static String LOG_TAG = HomeWeatherFragment.class.getName();
    private static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?id=1279233&units=metric&APPID=a17444f031c64a6cdee1faf544ef6494";

    public static HomeWeatherFragment newInstance() {
        // Required empty public constructor
        HomeWeatherFragment fragment = new HomeWeatherFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_weather, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SimpleDateFormat sdfday = new SimpleDateFormat("EEEE");
        String day = sdfday.format(today);
        TextView dayOfWeek = (TextView) getView().findViewById(R.id.day);
        dayOfWeek.setText(day);

        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM dd");
        String date = sdfMonth.format(today);
        TextView dateView = (TextView) getView().findViewById(R.id.date);
        dateView.setText(date);

        weather = (TextView)getView().findViewById(R.id.weather);
        weatherUnit = (TextView)getView().findViewById(R.id.weather_unit);

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            Log.i(LOG_TAG, "calling initLoader in weather");
            loaderManager.initLoader(2, null, this);
        }else{
            weather.setVisibility(View.GONE);
            weatherUnit.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<WeatherObject> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader() call hua h");
        return new WeatherLoader(getContext(), WEATHER_URL);
    }

    @Override
    public void onLoadFinished(Loader<WeatherObject> loader, WeatherObject data) {
        Log.i(LOG_TAG, "onLoadFinished() call ho gya h");

        currentWeather = null;

        if(data != null){
            currentWeather = data;

            weatherUnit.setVisibility(View.VISIBLE);
            weather.setText(currentWeather.getTemp());
            weather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.bottomNavigationView.getMenu().getItem(2).setChecked(true);
                    ((MainActivity)getActivity()).setFragmentInMain(WeatherFragment.newInstance());
                }
            });
        }else{
            weather.setVisibility(View.GONE);
            weatherUnit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<WeatherObject> loader) {
        Log.i(LOG_TAG, "onLoaderReset() call ho gya h");

        currentWeather = null;
    }
}
