package com.example.model;

import java.sql.Timestamp;

public class SearchHistory {
    private Long id;
    private double lat;
    private double lnt;
    private Timestamp searchTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLnt() {
        return lnt;
    }

    public void setLnt(double lnt) {
        this.lnt = lnt;
    }

    public Timestamp getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(Timestamp searchTime) {
        this.searchTime = searchTime;
    }
}
