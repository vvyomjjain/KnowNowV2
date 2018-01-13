package com.knownow.vyomjain.knownowv2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.knownow.vyomjain.knownowv2.data.NewsContract;
import com.knownow.vyomjain.knownowv2.data.NewsDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static android.support.design.widget.Snackbar.make;

public class SingleArticle extends AppCompatActivity {

    private String title;
    private String description;
    private String author;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String source;
    private static final Date TODAY = new Date();

    public static final String LOG_TAG = SingleArticle.class.getName();

    private NewsDbHelper mDbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_article);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_action_bar);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        author = getIntent().getStringExtra("author");
        url = getIntent().getStringExtra("newsUrl");
        urlToImage = getIntent().getStringExtra("urlToImage");
        publishedAt = getIntent().getStringExtra("publishedAt");
        source = getIntent().getStringExtra("source");

        TextView date = (TextView)findViewById(R.id.date);
        if(publishedAt != null){
            Date dateObject = getDate(publishedAt);

            if(dateObject != null){
                //formatting the date...
                String formattedDate = formatDate(dateObject);
                date.setText(formattedDate);
            }
        }

        source = source.replace('-',' ');
        TextView sourceView = (TextView)findViewById(R.id.source);
        sourceView.setText(source);

        TextView titleView = (TextView)findViewById(R.id.title);
        titleView.setText(title);

        TextView descView = (TextView)findViewById(R.id.desc);
        descView.setText(description);

        TextView authorView = (TextView)findViewById(R.id.author);
        if(author == null || author.equalsIgnoreCase("null")){
            authorView.setText("newsroom");
        }else
            authorView.setText(author);

        ImageView image = (ImageView)findViewById(R.id.newsImage);
        Glide
                .with(this)
                .load(urlToImage)
                .into(image);

        TextView read = (TextView)findViewById(R.id.read);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                customTabsIntent.launchUrl(SingleArticle.this, Uri.parse(url));
            }
        });

        mDbHelper = new NewsDbHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_menu, menu);
        return true;
    }

    private void insertArticle(){
        db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NewsContract.SavedEntry.COLUMN_TITLE, title);
        values.put(NewsContract.SavedEntry.COLUMN_AUTHOR, author);
        values.put(NewsContract.SavedEntry.COLUMN_SOURCE, source);
        values.put(NewsContract.SavedEntry.COLUMN_DESC, description);
        values.put(NewsContract.SavedEntry.COLUMN_PUBLISHED_AT, publishedAt);
        values.put(NewsContract.SavedEntry.COLUMN_URL, url);
        values.put(NewsContract.SavedEntry.COLUMN_URL_IMAGE, urlToImage);

        long newEntry = db.insert(NewsContract.SavedEntry.TABLE_NAME, null, values);

        if (newEntry == -1){
            make(findViewById(R.id.coord), "Already Saved!", Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar snackbar = Snackbar.make(findViewById(R.id.coord), "Saved", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] args = new String[1];
                    args[0] = url;
                    long delete = db.delete(NewsContract.SavedEntry.TABLE_NAME, NewsContract.SavedEntry.COLUMN_URL + " = ?", args);
                    Log.i(LOG_TAG, "Deleted = " + delete);
                    Snackbar snackbar1 = Snackbar.make(findViewById(R.id.coord), "Removed from saved list", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }
            });
            snackbar.show();
        }

        Log.i(LOG_TAG, "new Row: " + newEntry);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share_option:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                startActivity(Intent.createChooser(shareIntent, "Share Via..."));
                return true;

            case R.id.save_option:
                insertArticle();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static Date getDate(String dateString){
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(dateString);
            return date;
        }catch (Exception e){
            Log.i(LOG_TAG, "Exception hua hai be");
            e.printStackTrace();
            return null;
        }
    }

    private String formatDate(Date dateObject){
        long time = dateObject.getTime();
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - time;
        long s= TimeUnit.MILLISECONDS.toSeconds(diff);
        long m=TimeUnit.MILLISECONDS.toMinutes(diff);
        long h=TimeUnit.MILLISECONDS.toHours(diff);
        long d=TimeUnit.MILLISECONDS.toDays(diff);

        if(s<60)
        {
            return s + " seconds ago";
        }
        else if(m<60)
        {
            return m + " min ago";
        }
        else if(h<24)
        {
            return h + " hrs ago";
        }else if(d == 1){
            return "a day ago";
        }
        else
        {
            return d + " days ago";
        }
    }
}
