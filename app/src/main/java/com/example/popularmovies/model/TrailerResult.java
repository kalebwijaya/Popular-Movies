package com.example.popularmovies.model;

public class TrailerResult {
    private String id;
    private String iso639_1;
    private String iso3166_1;
    private String key;
    private String name;
    private String site;
    private long size;
    private String type;

    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public String getIso6391() {
        return iso639_1;
    }

    public void setIso6391(String value) {
        this.iso639_1 = value;
    }

    public String getIso31661() {
        return iso3166_1;
    }

    public void setIso31661(String value) {
        this.iso3166_1 = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String value) {
        this.key = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String value) {
        this.site = value;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long value) {
        this.size = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }
}
