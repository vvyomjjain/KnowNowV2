package com.knownow.vyomjain.knownowv2.data;

import android.provider.BaseColumns;

/**
 * Created by Vyom Jain on 26-09-2017.
 */

public final class NewsContract {

    public static abstract class SavedEntry implements BaseColumns{

        public static final String TABLE_NAME = "saved";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URL_IMAGE = "urlToImage";
        public static final String COLUMN_PUBLISHED_AT = "publishedAt";
        public static final String COLUMN_SOURCE = "source";
    }

    public static abstract class HistoryEntry implements BaseColumns{

        public static final String TABLE_NAME = "history";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URL_IMAGE = "urlToImage";
        public static final String COLUMN_PUBLISHED_AT = "publishedAt";
        public static final String COLUMN_SOURCE = "source";
    }
}
