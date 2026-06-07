package com.example.healthwise.models;

public class Article {

    private final String category;
    private final String title;
    private final String summary;
    private final String readTime;
    private final String body;

    public Article(String category, String title, String summary, String readTime, String body) {
        this.category = category;
        this.title = title;
        this.summary = summary;
        this.readTime = readTime;
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getReadTime() {
        return readTime;
    }

    public String getBody() {
        return body;
    }

    public String getMetaLabel() {
        return category + " - " + readTime + " read";
    }
}
