package com.knownow.vyomjain.knownowv2;


import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.knownow.vyomjain.knownowv2.data.NewsContract;
import com.knownow.vyomjain.knownowv2.data.NewsDbHelper;

import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment{

    LinearLayout history;
    LinearLayout saved;
    LinearLayout newsApi;
    LinearLayout weatherApi;

    private NewsDbHelper newsDbHelper;

    private static final String NEWS_API_URL = "https://newsapi.org/";
    private static final String WEATHER_API_URL = "https://openweathermap.org/";

    public static SettingsFragment newInstance(){
        SettingsFragment fragment = new SettingsFragment();
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        history = (LinearLayout)getActivity().findViewById(R.id.clear_hist);
        saved = (LinearLayout)getActivity().findViewById(R.id.clear_save);
        newsApi = (LinearLayout)getActivity().findViewById(R.id.news_api);
        weatherApi = (LinearLayout)getActivity().findViewById(R.id.weather_api);

        newsDbHelper = new NewsDbHelper(getContext());

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main_view), "Are you sure?", Snackbar.LENGTH_LONG);
                snackbar.setAction("DELETE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = newsDbHelper.getWritableDatabase();
                        db.execSQL("delete from " + NewsContract.HistoryEntry.TABLE_NAME);
                        Snackbar snackbar1 = Snackbar.make(getActivity().findViewById(R.id.main_view), "History deleted", Snackbar.LENGTH_LONG);
                        snackbar1.show();
                    }
                });
                snackbar.show();
            }
        });

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main_view), "Are you sure?", Snackbar.LENGTH_LONG);
                snackbar.setAction("DELETE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = newsDbHelper.getWritableDatabase();
                        db.execSQL("delete from " + NewsContract.SavedEntry.TABLE_NAME);
                        Snackbar snackbar1 = Snackbar.make(getActivity().findViewById(R.id.main_view), "All saved articles deleted", Snackbar.LENGTH_LONG);
                        snackbar1.show();
                    }
                });
                snackbar.show();
            }
        });

        newsApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                customTabsIntent.launchUrl(getContext(), Uri.parse(NEWS_API_URL));
            }
        });

        weatherApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                customTabsIntent.launchUrl(getContext(), Uri.parse(WEATHER_API_URL));
            }
        });
    }
}
