package com.example.popularmovies.model;

import java.io.IOException;
import java.util.List;
import java.time.LocalDate;

public class Movie {
    private Boolean adult;
    private String backdrop_path;
    private List<Long> genre_ids;
    private Long id;
    private OriginalLanguage original_language;
    private String original_title;
    private String overview;
    private Double popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private Boolean video;
    private Double vote_average;
    private Long vote_count;
    private String imagePath = new String();
    private Long runtime;
    private Boolean isFavorite = false;

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long value) {
        this.runtime = value;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Long> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Long> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OriginalLanguage getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(OriginalLanguage original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Long getVote_count() {
        return vote_count;
    }

    public void setVote_count(Long vote_count) {
        this.vote_count = vote_count;
    }


    enum OriginalLanguage {
        EN, FR, RU, ZH;

        public String toValue() {
            switch (this) {
                case EN:
                    return "en";
                case FR:
                    return "fr";
                case RU:
                    return "ru";
                case ZH:
                    return "zh";
            }
            return null;
        }

        public static OriginalLanguage forValue(String value) throws IOException {
            if (value.equals("en")) return EN;
            if (value.equals("fr")) return FR;
            if (value.equals("ru")) return RU;
            if (value.equals("zh")) return ZH;
            throw new IOException("Cannot deserialize OriginalLanguage");
        }
    }

}