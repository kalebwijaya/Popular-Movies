package com.example.popularmovies.model;

import java.util.List;

public class TrailerVideo {
    private long id;
    private List<TrailerResult> results;

    public long getID() {
        return id;
    }

    public void setID(long value) {
        this.id = value;
    }

    public List<TrailerResult> getResults() {
        return results;
    }

    public void setResults(List<TrailerResult> value) {
        this.results = value;
    }

}
