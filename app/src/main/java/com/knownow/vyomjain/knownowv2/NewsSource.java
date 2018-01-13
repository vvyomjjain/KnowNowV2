package com.knownow.vyomjain.knownowv2;

/**
 * Created by Vyom Jain on 02-09-2017.
 */

public class NewsSource {

    private String source_name;
    private int imageResourceId;
    private String url;

    public NewsSource(String source_name, int imageResourceId, String url){
        this.source_name = source_name;
        this.imageResourceId = imageResourceId;
        this.url = url;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getSource_name() {
        return source_name;
    }

    public String getUrl() {
        return url;
    }
}
