package com.example.popularmovies.model;

import java.util.Date;
import java.util.List;

public class Reviews {
    private Long id;
    private Long page;
    private List<ReviewResult> results;
    private Long total_pages;
    private Long total_results;

    public Long getID() {
        return id;
    }

    public void setID(Long value) {
        this.id = value;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long value) {
        this.page = value;
    }

    public List<ReviewResult> getResults() {
        return results;
    }

    public void setResults(List<ReviewResult> value) {
        this.results = value;
    }

    public Long getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(Long value) {
        this.total_pages = value;
    }

    public Long getTotalResults() {
        return total_results;
    }

    public void setTotalResults(Long value) {
        this.total_results = value;
    }

}
