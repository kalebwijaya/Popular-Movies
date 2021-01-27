package com.example.popularmovies.model;

import java.util.List;

public class MovieList {
    private long page;
    private List<Movie> results;
    private long total_pages;
    private long total_results;

    public long getPage() { return page; }
    public void setPage(long value) { this.page = value; }

    public List<Movie> getResults() { return results; }
    public void setResults(List<Movie> value) { this.results = value; }

    public long getTotalPages() { return total_pages; }
    public void setTotalPages(long value) { this.total_pages = value; }

    public long getTotalResults() { return total_results; }
    public void setTotalResults(long value) { this.total_results = value; }
}
