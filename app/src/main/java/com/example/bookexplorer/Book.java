package com.example.bookexplorer;

public class Book {

    private String title;
    private String author;
    private String imageUrl;
    private String publishedDate;


    public Book(String title, String author, String imageUrl, String publishedDate) {

        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;

    }


    public String getTitle() {
        return title;
    }


    public String getAuthor() {
        return author;
    }


    public String getImageUrl() {
        return imageUrl;
    }


    public String getPublishedDate() {
        return publishedDate;
    }

}