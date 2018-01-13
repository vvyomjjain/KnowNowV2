package com.knownow.vyomjain.knownowv2;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.knownow.vyomjain.knownowv2.data.NewsContract;
import com.knownow.vyomjain.knownowv2.data.NewsDbHelper;

import java.util.ArrayList;
import static com.knownow.vyomjain.knownowv2.R.id.saved;

public class SavedFragment extends Fragment {

    private NewsDbHelper mDbHelper;
    private ArticleAdapter mAdapter;
    private static final String LOG_TAG = SavedFragment.class.getName();
    Cursor cursor;
    ArrayList<Article> articleList;
    TextView savedView;
    TextView historyView;

    public static SavedFragment newInstance(){
        SavedFragment fragment = new SavedFragment();
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
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDbHelper = new NewsDbHelper(getContext());

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        articleList = new ArrayList<Article>();
        mAdapter = new ArticleAdapter(getActivity(), articleList);
        final ListView savedList =(ListView)getView().findViewById(R.id.saved_list);

        try{
            cursor = db.rawQuery("SELECT * FROM " + NewsContract.SavedEntry.TABLE_NAME, null);
            cursor.moveToFirst();

            try{
                do {
                    String author = cursor.getString(2);
                    String title = cursor.getString(0);
                    String description = cursor.getString(1);
                    String newsUrl = cursor.getString(3);
                    String urlToImage = cursor.getString(4);
                    String publishedAt = cursor.getString(6);
                    String source = cursor.getString(5);

                    Article article = new Article(title, description, author, newsUrl, urlToImage, publishedAt, source);
                    articleList.add(article);
                }while (cursor.moveToNext());

                mAdapter = new ArticleAdapter(getActivity(), articleList);
                savedList.setAdapter(mAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                cursor.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        savedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent articleIntent = new Intent(getActivity(), SavedSingleArticle.class);
                Article currentNews = (Article)savedList.getAdapter().getItem(position);
                String author = currentNews.getAuthor();
                String title = currentNews.getTitle();
                String description = currentNews.getDescription();
                String newsUrl = currentNews.getUrl();
                String urlToImage = currentNews.getUrlToImage();
                String publishedAt = currentNews.getPublishedAt();
                String source = currentNews.getSource();

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

        savedView = (TextView)getActivity().findViewById(saved);
        historyView = (TextView)getActivity().findViewById(R.id.history);

        savedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                savedView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                historyView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                historyView.setTextColor(getResources().getColor(R.color.colorAccent));

                mAdapter.clear();

                try{
                    cursor = db.rawQuery("SELECT * FROM " + NewsContract.SavedEntry.TABLE_NAME, null);
                    cursor.moveToFirst();

                    try{
                        do {
                            String author = cursor.getString(2);
                            String title = cursor.getString(0);
                            String description = cursor.getString(1);
                            String newsUrl = cursor.getString(3);
                            String urlToImage = cursor.getString(4);
                            String publishedAt = cursor.getString(6);
                            String source = cursor.getString(5);

                            Article article = new Article(title, description, author, newsUrl, urlToImage, publishedAt, source);
                            articleList.add(article);
                        }while (cursor.moveToNext());

                        mAdapter = new ArticleAdapter(getActivity(), articleList);
                        savedList.setAdapter(mAdapter);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        cursor.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                savedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent articleIntent = new Intent(getActivity(), SavedSingleArticle.class);
                        Article currentNews = (Article)savedList.getAdapter().getItem(position);
                        String author = currentNews.getAuthor();
                        String title = currentNews.getTitle();
                        String description = currentNews.getDescription();
                        String newsUrl = currentNews.getUrl();
                        String urlToImage = currentNews.getUrlToImage();
                        String publishedAt = currentNews.getPublishedAt();
                        String source = currentNews.getSource();

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
        });

        historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                historyView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                savedView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                savedView.setTextColor(getResources().getColor(R.color.colorAccent));

                mAdapter.clear();

                try{
                    cursor = db.rawQuery("SELECT * FROM " + NewsContract.HistoryEntry.TABLE_NAME + " ORDER BY " + NewsContract.HistoryEntry.COLUMN_PUBLISHED_AT + " DESC", null);
                    cursor.moveToFirst();

                    try{
                        do {
                            String author = cursor.getString(2);
                            String title = cursor.getString(0);
                            String description = cursor.getString(1);
                            String newsUrl = cursor.getString(3);
                            String urlToImage = cursor.getString(4);
                            String publishedAt = cursor.getString(6);
                            String source = cursor.getString(5);

                            Article article = new Article(title, description, author, newsUrl, urlToImage, publishedAt, source);
                            articleList.add(article);
                        }while (cursor.moveToNext());

                        mAdapter = new ArticleAdapter(getActivity(), articleList);
                        savedList.setAdapter(mAdapter);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        cursor.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                savedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent articleIntent = new Intent(getActivity(), SingleArticle.class);
                        Article currentNews = (Article)savedList.getAdapter().getItem(position);
                        String author = currentNews.getAuthor();
                        String title = currentNews.getTitle();
                        String description = currentNews.getDescription();
                        String newsUrl = currentNews.getUrl();
                        String urlToImage = currentNews.getUrlToImage();
                        String publishedAt = currentNews.getPublishedAt();
                        String source = currentNews.getSource();

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
        });
    }
}
