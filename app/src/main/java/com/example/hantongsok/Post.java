package com.example.hantongsok;

public class Post {
    private int postNumber;
    private String title;
    private String author;
    private String date;
    private String url;

    public Post(int postNumber, String title, String author, String date, String url) {
        this.postNumber = postNumber;
        this.title = title;
        this.author = author;
        this.date = date;
        this.url = url;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
}
