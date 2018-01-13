package com.knownow.vyomjain.knownowv2;

/**
 * Created by Vyom Jain on 02-09-2017.
 */

public class Article {

    private String title;
    private String description;
    private String author;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String source;

    public Article(String title, String description, String author, String url, String urlToImage, String publishedAt, String source){
        this.title = title;
        this.description = description;
        this.author = author;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getSource() {
        return source;
    }
}
