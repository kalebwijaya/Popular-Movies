package com.example.popularmovies.model;

public class AuthorDetails {
    private String name;
    private String username;
    private String avatar_path;
    private Long rating;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getAvatarPath() {
        return avatar_path;
    }

    public void setAvatarPath(String value) {
        this.avatar_path = value;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long value) {
        this.rating = value;
    }
}
