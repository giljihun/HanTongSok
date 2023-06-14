package com.example.hantongsok;

public class Post {
    private int postNumber;
    private String title;
    private String author;
    private String date;
    private String url;
    private String attachmentUrl;
    private String filename;

    public Post(int postNumber, String title, String author, String date, String url, String attachmentUrl, String filename) {
        this.postNumber = postNumber;
        this.title = title;
        this.author = author;
        this.date = date;
        this.url = url;
        this.attachmentUrl = attachmentUrl;
        this.filename = filename;
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
    public String getUrl() {
        return url;
    }
    public String getAttachmentUrl() { return attachmentUrl; }
    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }
    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

}
