package com.knownow.vyomjain.knownowv2;

import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.knownow.vyomjain.knownowv2.data.NewsContract;
import com.knownow.vyomjain.knownowv2.data.NewsDbHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.id.list;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.knownow.vyomjain.knownowv2.R.id.source;
import static com.knownow.vyomjain.knownowv2.SourceNewsActivity.NEWS_LOADER_ID;

public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Article>>{

    public static final String LOG_TAG = HomeFragment.class.getName();

    private NewsDbHelper mDbHelper;

    private ProgressBar progress1;
    private RecyclerView recycle1;
    private RecyclerView.Adapter adapter1;
    private RecyclerView.LayoutManager manager1;
    private ArrayList list1;

    private ProgressBar progress2;
    private RecyclerView recycle2;
    private RecyclerView.Adapter adapter2;
    private RecyclerView.LayoutManager manager2;
    private ArrayList list2;

    private ProgressBar progress3;
    private RecyclerView recycle3;
    private RecyclerView.Adapter adapter3;
    private RecyclerView.LayoutManager manager3;
    private ArrayList list3;

    private ProgressBar progress4;
    private RecyclerView recycle4;
    private RecyclerView.Adapter adapter4;
    private RecyclerView.LayoutManager manager4;
    private ArrayList list4;

    private ProgressBar progress5;
    private RecyclerView recycle5;
    private RecyclerView.Adapter adapter5;
    private RecyclerView.LayoutManager manager5;
    private ArrayList list5;

    private TextView mEmptyTextView;
    private ImageView offlineImage;
    private ScrollView scrollView;

    private static final String URL1 = "https://newsapi.org/v1/articles?source=the-verge&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6";
    private static final String URL2 = "https://newsapi.org/v1/articles?source=espn&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6";
    private static final String URL3 = "https://newsapi.org/v1/articles?source=cnbc&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6";
    private static final String URL4 = "https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6";
    private static final String URL5 = "https://newsapi.org/v1/articles?source=national-geographic&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6";

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mEmptyTextView = (TextView)getActivity().findViewById(R.id.empty_text);
        offlineImage = (ImageView)getActivity().findViewById(R.id.off_image);
        scrollView = (ScrollView) getActivity().findViewById(R.id.scroll);

        Fragment childFragment = HomeWeatherFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_weather_frag, childFragment).commit();

        list1 = new ArrayList<Article>();
        recycle1 = (RecyclerView)getActivity().findViewById(R.id.recycle1);
        recycle1.setHasFixedSize(true);
        manager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycle1.setLayoutManager(manager1);
        adapter1 = new NewsCardAdapter(getContext(), list1);
        recycle1.setAdapter(adapter1);
        progress1 = (ProgressBar)getActivity().findViewById(R.id.progress1);


        list2 = new ArrayList<Article>();
        recycle2 = (RecyclerView)getActivity().findViewById(R.id.recycle2);
        recycle2.setHasFixedSize(true);
        manager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycle2.setLayoutManager(manager2);
        adapter2 = new NewsCardAdapter(getContext(), list2);
        recycle2.setAdapter(adapter2);
        progress2 = (ProgressBar)getActivity().findViewById(R.id.progress2);

        list3 = new ArrayList<Article>();
        recycle3 = (RecyclerView)getActivity().findViewById(R.id.recycle3);
        recycle3.setHasFixedSize(true);
        manager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycle3.setLayoutManager(manager3);
        adapter3 = new NewsCardAdapter(getContext(), list3);
        recycle3.setAdapter(adapter3);
        progress3 = (ProgressBar)getActivity().findViewById(R.id.progress3);

        list4 = new ArrayList<Article>();
        recycle4 = (RecyclerView)getActivity().findViewById(R.id.recycle4);
        recycle4.setHasFixedSize(true);
        manager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycle4.setLayoutManager(manager4);
        adapter4 = new NewsCardAdapter(getContext(), list4);
        recycle4.setAdapter(adapter4);
        progress4 = (ProgressBar)getActivity().findViewById(R.id.progress4);

        list5 = new ArrayList<Article>();
        recycle5 = (RecyclerView)getActivity().findViewById(R.id.recycle5);
        recycle5.setHasFixedSize(true);
        manager5 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycle5.setLayoutManager(manager5);
        adapter5 = new NewsCardAdapter(getContext(), list5);
        recycle5.setAdapter(adapter5);
        progress5 = (ProgressBar)getActivity().findViewById(R.id.progress5);

        recycle1.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent articleIntent = new Intent(getActivity(), SingleArticle.class);
                        Article currentNews = (Article) list1.get(position);
                        String author = currentNews.getAuthor();
                        String title = currentNews.getTitle();
                        String description = currentNews.getDescription();
                        String newsUrl = currentNews.getUrl();
                        String urlToImage = currentNews.getUrlToImage();
                        String publishedAt = currentNews.getPublishedAt();
                        String source = currentNews.getSource();

                        mDbHelper = new NewsDbHelper(getContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(NewsContract.HistoryEntry.COLUMN_TITLE, title);
                        values.put(NewsContract.HistoryEntry.COLUMN_AUTHOR, author);
                        values.put(NewsContract.HistoryEntry.COLUMN_SOURCE, source);
                        values.put(NewsContract.HistoryEntry.COLUMN_DESC, description);
                        values.put(NewsContract.HistoryEntry.COLUMN_PUBLISHED_AT, publishedAt);
                        values.put(NewsContract.HistoryEntry.COLUMN_URL, newsUrl);
                        values.put(NewsContract.HistoryEntry.COLUMN_URL_IMAGE, urlToImage);

                        long newEntry = db.insert(NewsContract.HistoryEntry.TABLE_NAME, null,values);
                        if (newEntry == -1){
                            String[] args = new String[1];
                            args[0] = newsUrl;
                            long delete = db.delete(NewsContract.HistoryEntry.TABLE_NAME, NewsContract.HistoryEntry.COLUMN_URL + " = ?", args);
                            Log.i(LOG_TAG, "Deleted = " + delete);

                            newEntry = db.insert(NewsContract.HistoryEntry.TABLE_NAME, null,values);
                        }else{
                            Log.i(LOG_TAG, "Added to History");
                        }

                        Log.i(LOG_TAG, "new Row: " + newEntry);

                        articleIntent.putExtra("author", author);
                        articleIntent.putExtra("title", title);
                        articleIntent.putExtra("description", description);
                        articleIntent.putExtra("newsUrl", newsUrl);
                        articleIntent.putExtra("urlToImage", urlToImage);
                        articleIntent.putExtra("publishedAt", publishedAt);
                        articleIntent.putExtra("source", source);

                        startActivity(articleIntent);
                    }
                })
        );

        recycle2.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent articleIntent = new Intent(getActivity(), SingleArticle.class);
                        Article currentNews = (Article) list2.get(position);
                        String author = currentNews.getAuthor();
                        String title = currentNews.getTitle();
                        String description = currentNews.getDescription();
                        String newsUrl = currentNews.getUrl();
                        String urlToImage = currentNews.getUrlToImage();
                        String publishedAt = currentNews.getPublishedAt();
                        String source = currentNews.getSource();

                        mDbHelper = new NewsDbHelper(getContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(NewsContract.HistoryEntry.COLUMN_TITLE, title);
                        values.put(NewsContract.HistoryEntry.COLUMN_AUTHOR, author);
                        values.put(NewsContract.HistoryEntry.COLUMN_SOURCE, source);
                        values.put(NewsContract.HistoryEntry.COLUMN_DESC, description);
                        values.put(NewsContract.HistoryEntry.COLUMN_PUBLISHED_AT, publishedAt);
                        values.put(NewsContract.HistoryEntry.COLUMN_URL, newsUrl);
                        values.put(NewsContract.HistoryEntry.COLUMN_URL_IMAGE, urlToImage);

                        long newEntry = db.insert(NewsContract.HistoryEntry.TABLE_NAME, null,values);
                        if (newEntry == -1){
                            String[] args = new String[1];
                            args[0] = newsUrl;
                            long delete = db.delete(NewsContract.HistoryEntry.TABLE_NAME, NewsContract.HistoryEntry.COLUMN_URL + " = ?", args);
                            Log.i(LOG_TAG, "Deleted = " + delete);

                            newEntry = db.insert(NewsContract.HistoryEntry.TABLE_NAME, null,values);
                        }else{
                            Log.i(LOG_TAG, "Added to History");
                        }

                        Log.i(LOG_TAG, "new Row: " + newEntry);

                        articleIntent.putExtra("author", author);
                        articleIntent.putExtra("title", title);
                        articleIntent.putExtra("description", description);
                        articleIntent.putExtra("newsUrl", newsUrl);
                        articleIntent.putExtra("urlToImage", urlToImage);
                        articleIntent.putExtra("publishedAt", publishedAt);
                        articleIntent.putExtra("source", source);

                        startActivity(articleIntent);
                    }
                })
        );

        recycle3.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Intent articleIntent = new Intent(getActivity(), SingleArticle.class);
                        Article currentNews = (Article) list3.get(position);
                        String author = currentNews.getAuthor();
                        String title = currentNews.getTitle();
                        String description = currentNews.getDescription();
                        String newsUrl = currentNews.getUrl();
                        String urlToImage = currentNews.getUrlToImage();
                        String publishedAt = currentNews.getPublishedAt();
                        String source = currentNews.getSource();

                        mDbHelper = new NewsDbHelper(getContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(NewsContract.HistoryEntry.COLUMN_TITLE, title);
                        values.put(NewsContract.HistoryEntry.COLUMN_AUTHOR, author);
                        values.put(NewsContract.HistoryEntry.COLUMN_SOURCE, source);
                        values.put(NewsContract.HistoryEntry.COLUMN_DESC, description);
                        values.put(NewsContract.HistoryEntry.COLUMN_PUBLISHED_AT, publishedAt);
                        values.put(NewsContract.HistoryEntry.COLUMN_URL, newsUrl);
                        values.put(NewsContract.HistoryEntry.COLUMN_URL_IMAGE, urlToImage);

                        long newEntry = db.insert(NewsContract.HistoryEntry.TABLE_NAME, null,values);
                        if (newEntry == -1){
                            String[] args = new String[1];
                            args[0] = newsUrl;
                            long delete = db.delete(NewsContract.HistoryEntry.TABLE_NAME, NewsContract.HistoryEntry.COLUMN_URL + " = ?", args);
                            Log.i(LOG_TAG, "Deleted = " + delete);

                            newEntry = db.insert(NewsContract.HistoryEntry.TABLE_NAME, null,values);
                        }else{
                            Log.i(LOG_TAG, "Added to History");
                        }

                        Log.i(LOG_TAG, "new Row: " + newEntry);

                        articleIntent.putExtra("author", author);
                        articleIntent.putExtra("title", title);
                        articleIntent.putExtra("description", description);
                        articleIntent.putExtra("newsUrl", newsUrl);
                        articleIntent.putExtra("urlToImage", urlToImage);
                        articleIntent.putExtra("publishedAt", publishedAt);
                        articleIntent.putExtra("source", source);

                        startActivity(articleIntent);
                    }
                })
        );

        recycle4.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent articleIntent = new Intent(getActivity(), SingleArticle.class);
                        Article currentNews = (Article) list4.get(position);
                        String author = currentNews.getAuthor();
                        String title = currentNews.getTitle();
                        String description = currentNews.getDescription();
                        String newsUrl = currentNews.getUrl();
                        String urlToImage = currentNews.getUrlToImage();
                        String publishedAt;
                        if(currentNews.getPublishedAt() != null){
                            publishedAt = currentNews.getPublishedAt();
                        }else{
                            publishedAt = (new Date()).toString();
                        }
                        String source = currentNews.getSource();

                        mDbHelper = new NewsDbHelper(getContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(NewsContract.HistoryEntry.COLUMN_TITLE, title);
                        values.put(NewsContract.HistoryEntry.COLUMN_AUTHOR, author);
                        values.put(NewsContract.HistoryEntry.COLUMN_SOURCE, source);
                        values.put(NewsContract.HistoryEntry.COLUMN_DESC, description);
                        values.put(NewsContract.HistoryEntry.COLUMN_PUBLISHED_AT, publishedAt);
                        values.put(NewsContract.HistoryEntry.COLUMN_URL, newsUrl);
                        values.put(NewsContract.HistoryEntry.COLUMN_URL_IMAGE, urlToImage);

                        long newEntry = db.insert(NewsContract.HistoryEntry.TABLE_NAME, null,values);
                        if (newEntry == -1){
                            String[] args = new String[1];
                            args[0] = newsUrl;
                            long delete = db.delete(NewsContract.HistoryEntry.TABLE_NAME, NewsContract.HistoryEntry.COLUMN_URL + " = ?", args);
                            Log.i(LOG_TAG, "Deleted = " + delete);

                            newEntry = db.insert(NewsContract.HistoryEntry.TABLE_NAME, null,values);
                        }else{
                            Log.i(LOG_TAG, "Added to History");
                        }

                        Log.i(LOG_TAG, "new Row: " + newEntry);

                        articleIntent.putExtra("author", author);
                        articleIntent.putExtra("title", title);
                        articleIntent.putExtra("description", description);
                        articleIntent.putExtra("newsUrl", newsUrl);
                        articleIntent.putExtra("urlToImage", urlToImage);
                        articleIntent.putExtra("publishedAt", publishedAt);
                        articleIntent.putExtra("source", source);

                        startActivity(articleIntent);
                    }
                })
        );

        recycle5.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent articleIntent = new Intent(getActivity(), SingleArticle.class);
                        Article currentNews = (Article) list5.get(position);
                        String author = currentNews.getAuthor();
                        String title = currentNews.getTitle();
                        String description = currentNews.getDescription();
                        String newsUrl = currentNews.getUrl();
                        String urlToImage = currentNews.getUrlToImage();
                        String publishedAt = currentNews.getPublishedAt();
                        String source = currentNews.getSource();

                        mDbHelper = new NewsDbHelper(getContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(NewsContract.HistoryEntry.COLUMN_TITLE, title);
                        values.put(NewsContract.HistoryEntry.COLUMN_AUTHOR, author);
                        values.put(NewsContract.HistoryEntry.COLUMN_SOURCE, source);
                        values.put(NewsContract.HistoryEntry.COLUMN_DESC, description);
                        values.put(NewsContract.HistoryEntry.COLUMN_PUBLISHED_AT, publishedAt);
                        values.put(NewsContract.HistoryEntry.COLUMN_URL, newsUrl);
                        values.put(NewsContract.HistoryEntry.COLUMN_URL_IMAGE, urlToImage);

                        long newEntry = db.insert(NewsContract.HistoryEntry.TABLE_NAME, null,values);
                        if (newEntry == -1){
                            String[] args = new String[1];
                            args[0] = newsUrl;
                            long delete = db.delete(NewsContract.HistoryEntry.TABLE_NAME, NewsContract.HistoryEntry.COLUMN_URL + " = ?", args);
                            Log.i(LOG_TAG, "Deleted = " + delete);

                            newEntry = db.insert(NewsContract.HistoryEntry.TABLE_NAME, null,values);
                        }else{
                            Log.i(LOG_TAG, "Added to History");
                        }

                        Log.i(LOG_TAG, "new Row: " + newEntry);

                        articleIntent.putExtra("author", author);
                        articleIntent.putExtra("title", title);
                        articleIntent.putExtra("description", description);
                        articleIntent.putExtra("newsUrl", newsUrl);
                        articleIntent.putExtra("urlToImage", urlToImage);
                        articleIntent.putExtra("publishedAt", publishedAt);
                        articleIntent.putExtra("source", source);

                        startActivity(articleIntent);
                    }
                })
        );

        ConnectivityManager mngr = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mngr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            Log.i(LOG_TAG, "TEST: calling initLoader.");
            offlineImage.setVisibility(View.INVISIBLE);
            loaderManager.initLoader(1, null, this);
            loaderManager.initLoader(2, null, this);
            loaderManager.initLoader(3, null, this);
            loaderManager.initLoader(4, null, this);
            loaderManager.initLoader(5, null, this);
        }else{
            scrollView.setVisibility(View.GONE);
            offlineImage.setVisibility(View.VISIBLE);
            mEmptyTextView.setText("You are Offline.");
        }

    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle){
        Log.i(LOG_TAG, "TEST: onCreateLoader called.");

        switch (i){
            case 1:
                return new ArticleLoader(getContext(), URL1);

            case 2:
                return new ArticleLoader(getContext(), URL2);

            case 3:
                return new ArticleLoader(getContext(), URL3);

            case 4:
                return new ArticleLoader(getContext(), URL4);

            default:
                return new ArticleLoader(getContext(), URL5);
        }
    }
    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> earthquakes) {
        Log.i(LOG_TAG, "TEST: onLoadFinished called.");

        switch (loader.getId()){
            case 1:
                list1.clear();
                if (earthquakes != null && !earthquakes.isEmpty()){
                    list1.addAll(earthquakes);
                    Log.i(LOG_TAG, "list me add ho gye h");
                    adapter1.notifyDataSetChanged();
                    progress1.setVisibility(View.GONE);
                }
                break;

            case 2:
                list2.clear();
                if (earthquakes != null && !earthquakes.isEmpty()){
                    list2.addAll(earthquakes);
                    Log.i(LOG_TAG, "list me add ho gye h");
                    adapter2.notifyDataSetChanged();
                    progress2.setVisibility(View.GONE);
                }
                break;

            case 3:
                list3.clear();
                if (earthquakes != null && !earthquakes.isEmpty()){
                    list3.addAll(earthquakes);
                    Log.i(LOG_TAG, "list me add ho gye h");
                    adapter3.notifyDataSetChanged();
                    progress3.setVisibility(View.GONE);
                }
                break;

            case 4:
                list4.clear();
                if (earthquakes != null && !earthquakes.isEmpty()){
                    list4.addAll(earthquakes);
                    Log.i(LOG_TAG, "list me add ho gye h");
                    adapter4.notifyDataSetChanged();
                    progress4.setVisibility(View.GONE);
                }
                break;

            case 5:
                list5.clear();
                if (earthquakes != null && !earthquakes.isEmpty()){
                    list5.addAll(earthquakes);
                    Log.i(LOG_TAG, "list me add ho gye h");
                    adapter5.notifyDataSetChanged();
                    progress5.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

        Log.i(LOG_TAG, "TEST: onLoaderReset called.");
    }
}
