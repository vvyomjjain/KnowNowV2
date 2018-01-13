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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherForecastFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<WeatherForecastObject>>{

    RecyclerView recycler1;
    RecyclerView recycler2;
    RecyclerView recycler3;
    RecyclerView recycler4;
    RecyclerView recycler5;

    ArrayList list1;
    LinearLayoutManager manager1;
    ForecastAdapter adapter1;

    ArrayList list2;
    LinearLayoutManager manager2;
    ForecastAdapter adapter2;

    ArrayList list3;
    LinearLayoutManager manager3;
    ForecastAdapter adapter3;

    ArrayList list4;
    LinearLayoutManager manager4;
    ForecastAdapter adapter4;

    ArrayList list5;
    LinearLayoutManager manager5;
    ForecastAdapter adapter5;

    TextView date1;
    TextView date2;
    TextView date3;
    TextView date4;
    TextView date5;
    ArrayList list;
    int n;

    public static final String LOG_TAG = WeatherForecastFragment.class.getName();

    public static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q=Ahmedabad&cnt=40&units=metric&APPID=a17444f031c64a6cdee1faf544ef6494";

    public static WeatherForecastFragment newInstance() {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
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
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        date1 = (TextView)getActivity().findViewById(R.id.date1);
        date1.setText("Today");

        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        String hour = sdf.format(new Date());
        n = (24 - Integer.parseInt(hour))/3;

        list = new ArrayList();

        date2 = (TextView)getActivity().findViewById(R.id.date2);
        date2.setText("Tomorrow");

        date3 = (TextView)getActivity().findViewById(R.id.date3);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DAY_OF_YEAR, 2);
        Date dateObject3 = calendar3.getTime();
        date3.setText(formattedDate(dateObject3));

        date4 = (TextView)getActivity().findViewById(R.id.date4);
        Calendar calendar4 = Calendar.getInstance();
        calendar4.add(Calendar.DAY_OF_YEAR, 3);
        Date dateObject4 = calendar4.getTime();
        date4.setText(formattedDate(dateObject4));

        date5 = (TextView)getActivity().findViewById(R.id.date5);
        Calendar calendar5 = Calendar.getInstance();
        calendar5.add(Calendar.DAY_OF_YEAR, 4);
        Date dateObject5 = calendar5.getTime();
        date5.setText(formattedDate(dateObject5));

        recycler1 = (RecyclerView)getActivity().findViewById(R.id.recycle1);
        list1 = new ArrayList();
        recycler1.setHasFixedSize(true);
        manager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler1.setLayoutManager(manager1);
        adapter1 = new ForecastAdapter(getContext(), list1);
        recycler1.setAdapter(adapter1);

        recycler2 = (RecyclerView)getActivity().findViewById(R.id.recycle2);
        list2 = new ArrayList();
        recycler2.setHasFixedSize(true);
        manager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler2.setLayoutManager(manager2);
        adapter2 = new ForecastAdapter(getContext(), list2);
        recycler2.setAdapter(adapter2);

        recycler3 = (RecyclerView)getActivity().findViewById(R.id.recycle3);
        list3 = new ArrayList();
        recycler3.setHasFixedSize(true);
        manager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler3.setLayoutManager(manager3);
        adapter3 = new ForecastAdapter(getContext(), list3);
        recycler3.setAdapter(adapter3);

        recycler4 = (RecyclerView)getActivity().findViewById(R.id.recycle4);
        list4 = new ArrayList();
        recycler4.setHasFixedSize(true);
        manager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler4.setLayoutManager(manager4);
        adapter4 = new ForecastAdapter(getContext(), list4);
        recycler4.setAdapter(adapter4);

        recycler5 = (RecyclerView)getActivity().findViewById(R.id.recycle5);
        list5 = new ArrayList();
        recycler5.setHasFixedSize(true);
        manager5 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler5.setLayoutManager(manager5);
        adapter5 = new ForecastAdapter(getContext(), list5);
        recycler5.setAdapter(adapter5);

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

            int i=0;
            Log.i(LOG_TAG, "n = "+n);
            for(;i<n; i++){
                list1.add(list.get(i));
            }
            adapter1.notifyDataSetChanged();
            for(int j=0; j<n; j++){
                list2.add(list.get(i));
                i++;
            }
            adapter2.notifyDataSetChanged();
            for(int j=0; j<n; j++){
                list3.add(list.get(i));
                i++;
            }
            adapter3.notifyDataSetChanged();
            for(int j=0; j<n; j++){
                list4.add(list.get(i));
                i++;
            }
            adapter4.notifyDataSetChanged();
            for(int j=0; j<n; j++){
                list5.add(list.get(i));
                i++;
            }
            adapter5.notifyDataSetChanged();
        }else{
            Log.i(LOG_TAG, "ye kya ho gya re");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<WeatherForecastObject>> loader) {
        list.clear();
        list1.clear();
        list2.clear();
        list3.clear();
        list4.clear();
        list5.clear();

    }

    public static String formattedDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM");
        String day = dateFormat.format(date);
        return day;
    }
}
