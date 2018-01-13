package com.knownow.vyomjain.knownowv2;

import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherCurrentFragment extends Fragment implements LoaderManager.LoaderCallbacks<WeatherObject>{

    private static final String LOG_TAG = WeatherCurrentFragment.class.getName();
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?id=1279233&units=metric&APPID=a17444f031c64a6cdee1faf544ef6494";

    TextView humidity;
    ImageView icon;
    ProgressBar mCircularProgressBar;
    TextView mEmptyTextView;
    ImageView offlineImg;
    LinearLayout main;
    LinearLayout empty;
    RelativeLayout faltu;
    TextView pressure;
    TextView windSeed;
    TextView windDirection;
    TextView tempMin;
    TextView tempMax;
    TextView visibility;
    TextView clouds;
    WeatherObject currentWeather = null;
    TextView temp;
    TextView city;
    TextView description;

    public static WeatherCurrentFragment newInstance() {
        WeatherCurrentFragment fragment = new WeatherCurrentFragment();
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
        return inflater.inflate(R.layout.fragment_weather_current, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Fragment childFragment = TodayForeCast.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.upcoming, childFragment).commit();

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        mCircularProgressBar = (ProgressBar)getView().findViewById(R.id.progress);
        mEmptyTextView = (TextView)getView().findViewById(R.id.empty_text);
        offlineImg = (ImageView)getView().findViewById(R.id.off_image);
        main = (LinearLayout)getView().findViewById(R.id.main_view);
        empty = (LinearLayout)getView().findViewById(R.id.empty_view);
        faltu = (RelativeLayout)getView().findViewById(R.id.faltu);

        if (networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            Log.i(LOG_TAG, "calling initLoader in weather");
            loaderManager.initLoader(1, null, this);
        }else{
            mCircularProgressBar.setVisibility(View.GONE);
            offlineImg.setVisibility(View.VISIBLE);
            mEmptyTextView.setText("You are Offline.");
        }

        temp = (TextView)getActivity().findViewById(R.id.temp);
        city = (TextView)getActivity().findViewById(R.id.city);
        description = (TextView)getActivity().findViewById(R.id.description);
        humidity = (TextView)getActivity().findViewById(R.id.humidity);
        icon = (ImageView)getActivity().findViewById(R.id.icon);
        pressure = (TextView)getActivity().findViewById(R.id.pressure);
        windSeed = (TextView)getActivity().findViewById(R.id.wind_speed);
        windDirection = (TextView)getActivity().findViewById(R.id.wind_direction);
        tempMin = (TextView)getActivity().findViewById(R.id.temp_low);
        tempMax = (TextView)getActivity().findViewById(R.id.temp_high);
        visibility = (TextView)getActivity().findViewById(R.id.visibility);
        clouds = (TextView)getActivity().findViewById(R.id.clouds);
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

            temp.setText(currentWeather.getTemp());
            city.setText(currentWeather.getCityCountry());
            description.setText(currentWeather.getDescription());
            humidity.setText(currentWeather.getHumidity() + "%");
            pressure.setText(currentWeather.getPressure() + " hPa");
            windSeed.setText(currentWeather.getWindSpeed() + " m/s");
            windDirection.setText(currentWeather.getWindDirection() + "\u00B0");
            tempMin.setText(currentWeather.getTempMin() + "\u00B0");
            tempMax.setText(currentWeather.getTempMax() + "\u00B0");
            visibility.setText(currentWeather.getVisibility() + " m");
            clouds.setText(currentWeather.getClouds() + "%");

            switch (currentWeather.getIcon()){
                case "01d":
                    icon.setImageResource(R.drawable.w_01d);
                    break;

                case "01n":
                    icon.setImageResource(R.drawable.w_01n);
                    break;

                case "02d":
                    icon.setImageResource(R.drawable.w_02d);
                    break;

                case "02n":
                    icon.setImageResource(R.drawable.w_02n);
                    break;

                case "03d":
                    icon.setImageResource(R.drawable.w_03d);
                    break;

                case "03n":
                    icon.setImageResource(R.drawable.w_03n);
                    break;

                case "04d":
                    icon.setImageResource(R.drawable.w_04d);
                    break;

                case "04n":
                    icon.setImageResource(R.drawable.w_04n);
                    break;

                case "09d":
                    icon.setImageResource(R.drawable.w_09d);
                    break;

                case "09n":
                    icon.setImageResource(R.drawable.w_09n);
                    break;

                case "10d":
                    icon.setImageResource(R.drawable.w_10d);
                    break;

                case "10n":
                    icon.setImageResource(R.drawable.w_10n);
                    break;

                case "11d":
                    icon.setImageResource(R.drawable.w_11d);
                    break;

                case "11n":
                    icon.setImageResource(R.drawable.w_11n);
                    break;

                case "13d":
                    icon.setImageResource(R.drawable.w_13d);
                    break;

                case "13n":
                    icon.setImageResource(R.drawable.w_13n);
                    break;

                case "50d":
                    icon.setImageResource(R.drawable.w_50d);
                    break;

                case "50n":
                    icon.setImageResource(R.drawable.w_50n);
                    break;
            }

            main.setVisibility(View.VISIBLE);
            faltu.setVisibility(View.GONE);
        }else{
            main.setVisibility(View.GONE);
            mCircularProgressBar.setVisibility(View.GONE);
            offlineImg.setVisibility(View.VISIBLE);
            mEmptyTextView.setText("Can't fetch weather.");
        }
    }

    @Override
    public void onLoaderReset(Loader<WeatherObject> loader) {
        Log.i(LOG_TAG, "onLoaderReset() call ho gya h");

        currentWeather = null;
    }
}
