package com.example.popularmovies.model;

import java.util.Date;

public class ReviewResult {
    private String author;
    private AuthorDetails author_details;
    private String content;
    private Date created_at;
    private String id;
    private Date updated_at;
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String value) {
        this.author = value;
    }

    public AuthorDetails getAuthorDetails() {
        return author_details;
    }

    public void setAuthorDetails(AuthorDetails value) {
        this.author_details = value;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String value) {
        this.content = value;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date value) {
        this.created_at = value;
    }

    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date value) {
        this.updated_at = value;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String value) {
        this.url = value;
    }

}