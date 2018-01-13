package com.knownow.vyomjain.knownowv2;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.knownow.vyomjain.knownowv2.data.NewsContract;
import com.knownow.vyomjain.knownowv2.data.NewsDbHelper;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class SourceNewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>>{

    private static final String LOG_TAG = SourceNewsActivity.class.getName();
    private static String url;
    private static String name;
    public static final int NEWS_LOADER_ID = 1;
    private ArticleAdapter mAdapter;
    ProgressBar mCircularProgressBar;
    private TextView mEmptyTextView;
    private NewsDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_news);

        name = getIntent().getStringExtra("sourceName");
        getSupportActionBar().setTitle(name);
        url = getIntent().getStringExtra("url");

        final ListView listView = (ListView)findViewById(R.id.source_news_list);
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());
        listView.setAdapter(mAdapter);

        ConnectivityManager mngr = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mngr.getActiveNetworkInfo();
        mCircularProgressBar = (ProgressBar)findViewById(R.id.progress);
        mEmptyTextView = (TextView)findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyTextView);

        if (networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            Log.i(LOG_TAG, "TEST: calling initLoader.");
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }else {
            mCircularProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText("No Internet Connection.");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent articleIntent = new Intent(SourceNewsActivity.this, SingleArticle.class);
                Article currentNews = (Article)listView.getAdapter().getItem(position);
                String author = currentNews.getAuthor();
                String title = currentNews.getTitle();
                String description = currentNews.getDescription();
                String newsUrl = currentNews.getUrl();
                String urlToImage = currentNews.getUrlToImage();
                String publishedAt = currentNews.getPublishedAt();
                String source = currentNews.getSource();

                mDbHelper = new NewsDbHelper(SourceNewsActivity.this);
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
                    Toast.makeText(SourceNewsActivity.this, "Added to History", Toast.LENGTH_SHORT).show();
                }

                articleIntent.putExtra("author", author);
                articleIntent.putExtra("title", title);
                articleIntent.putExtra("description", description);
                articleIntent.putExtra("newsUrl", newsUrl);
                articleIntent.putExtra("urlToImage", urlToImage);
                articleIntent.putExtra("publishedAt", publishedAt);
                articleIntent.putExtra("source", source);

                startActivity(articleIntent);
            }
        });
    }

    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle){
        Log.i(LOG_TAG, "TEST: onCreateLoader called.");
        return new ArticleLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> earthquakes) {
        Log.i(LOG_TAG, "TEST: onLoadFinished called.");

        mAdapter.clear();

        if (earthquakes != null && !earthquakes.isEmpty()){
            mAdapter.addAll(earthquakes);
        }

        mCircularProgressBar.setVisibility(View.INVISIBLE);
        mEmptyTextView.setText("Couldn't fetch news.");

    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

        Log.i(LOG_TAG, "TEST: onLoaderReset called.");
        mAdapter.clear();
    }
}
