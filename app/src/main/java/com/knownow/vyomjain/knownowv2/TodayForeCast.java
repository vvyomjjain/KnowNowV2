package com.knownow.vyomjain.knownowv2;


import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayForeCast extends Fragment implements LoaderManager.LoaderCallbacks<List<WeatherForecastObject>>{

    RecyclerView recyclerView;
    ArrayList list;
    LinearLayoutManager manager;
    ForecastAdapter adapter;
    private static final String LOG_TAG = TodayForeCast.class.getName();
    public static String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?id=1279233&units=metric&cnt=8&APPID=a17444f031c64a6cdee1faf544ef6494";

    public static TodayForeCast newInstance() {
        TodayForeCast fragment = new TodayForeCast();
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
        return inflater.inflate(R.layout.fragment_today_fore_cast, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView)getActivity().findViewById(R.id.forecast_list);
        list = new ArrayList();
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new ForecastAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        ConnectivityManager mngr = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mngr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            Log.i(LOG_TAG, "TEST: calling initLoader.");
//            offlineImage.setVisibility(View.INVISIBLE);
            loaderManager.initLoader(1, null, this);
        }else{
//            mCircularProgressBar.setVisibility(View.GONE);
//            offlineImage.setVisibility(View.VISIBLE);
//            mEmptyTextView.setText("You are Offline.");
        }
    }

    @Override
    public Loader<List<WeatherForecastObject>> onCreateLoader(int id, Bundle args) {
        return new WeatherForecastLoader(getContext(), FORECAST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<WeatherForecastObject>> loader, List<WeatherForecastObject> data) {
        list.clear();
        if(data != null && !data.isEmpty()){
            list.addAll(data);
            Log.i(LOG_TAG, "list me add ho gye h");
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<WeatherForecastObject>> loader) {
        list.clear();
    }
}
