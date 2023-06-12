package com.example.hantongsok;

public class Post {
    private int postNumber;
    private String title;
    private String author;
    private String date;

    public Post(int postNumber, String title, String author, String date) {
        this.postNumber = postNumber;
        this.title = title;
        this.author = author;
        this.date = date;
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
