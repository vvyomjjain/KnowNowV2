package com.knownow.vyomjain.knownowv2;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Vyom Jain on 02-09-2017.
 */

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private static final String LOG_TAG = ArticleLoader.class.getName();

    //query url
    private String murl;

    public ArticleLoader(Context context, String url){
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading(){
        Log.i(LOG_TAG, "TEST: onStartLoading called.");
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground(){
        Log.i(LOG_TAG, "TEST: loadInBackground called.");
        if(murl == null){
            return null;
        }

        List<Article> earthquakes = QueryUtils.fetchNews(murl);
        return earthquakes;
    }
}
