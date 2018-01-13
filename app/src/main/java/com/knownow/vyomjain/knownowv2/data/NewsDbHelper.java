package com.knownow.vyomjain.knownowv2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.knownow.vyomjain.knownowv2.data.NewsContract.SavedEntry;
import com.knownow.vyomjain.knownowv2.data.NewsContract.HistoryEntry;

/**
 * Created by Vyom Jain on 26-09-2017.
 */

public class NewsDbHelper extends SQLiteOpenHelper {

    //Name of database
    public static final String DATABASE_NAME = "KnowNow.db";

    //data base version
    //will be incremented if changes are there in schema
    public static final int DATABASE_VERSION = 1;

    public NewsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //table will be created here
        String SQL_CREATE_TABLE = "CREATE TABLE " + SavedEntry.TABLE_NAME + "("
                + SavedEntry.COLUMN_TITLE + " TEXT,"
                + SavedEntry.COLUMN_DESC + " TEXT,"
                + SavedEntry.COLUMN_AUTHOR + " TEXT,"
                + SavedEntry.COLUMN_URL + " TEXT PRIMARY KEY,"
                + SavedEntry.COLUMN_URL_IMAGE + " TEXT,"
                + SavedEntry.COLUMN_SOURCE + " TEXT,"
                + SavedEntry.COLUMN_PUBLISHED_AT + " TEXT);";

        db.execSQL(SQL_CREATE_TABLE);

        String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + HistoryEntry.TABLE_NAME + "("
                + HistoryEntry.COLUMN_TITLE + " TEXT,"
                + HistoryEntry.COLUMN_DESC + " TEXT,"
                + HistoryEntry.COLUMN_AUTHOR + " TEXT,"
                + HistoryEntry.COLUMN_URL + " TEXT PRIMARY KEY,"
                + HistoryEntry.COLUMN_URL_IMAGE + " TEXT,"
                + HistoryEntry.COLUMN_SOURCE + " TEXT,"
                + HistoryEntry.COLUMN_PUBLISHED_AT + " TEXT);";

        db.execSQL(SQL_CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
