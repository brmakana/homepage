package com.pineapplewedding.homepage.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NewsFeedRepository {

    private Date lastUpdatedDate = new Date();
    private List<NewsFeed> feeds;

    public NewsFeedRepository() {
        feeds = new ArrayList<>();
    }

    public List<NewsFeed> getNewsFeeds() {
        return this.feeds;
    }

    public void setNewsFeeds(List<NewsFeed> feeds) {
        this.feeds = feeds;
        this.lastUpdatedDate = new Date();
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

}
