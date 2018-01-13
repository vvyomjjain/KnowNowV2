package com.knownow.vyomjain.knownowv2;


import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.TextView;

public class WeatherFragment extends Fragment{

    public Fragment selectedWeatherFragment;
    public TextView current;
    public TextView forecast;

    public static WeatherFragment newInstance(){
        WeatherFragment fragment = new WeatherFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        current = (TextView)getActivity().findViewById(R.id.current);
        forecast = (TextView)getActivity().findViewById(R.id.forecast);

        selectedWeatherFragment = WeatherCurrentFragment.newInstance();

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                current.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                forecast.setTextColor(getResources().getColor(R.color.colorAccent));
                forecast.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                selectedWeatherFragment = WeatherCurrentFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.selected_frag, selectedWeatherFragment);
                transaction.commit();
            }
        });

        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forecast.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                forecast.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                current.setTextColor(getResources().getColor(R.color.colorAccent));
                current.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                selectedWeatherFragment = WeatherForecastFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.selected_frag, selectedWeatherFragment);
                transaction.commit();
            }
        });

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.selected_frag, selectedWeatherFragment);
        transaction.commit();

    }
}
