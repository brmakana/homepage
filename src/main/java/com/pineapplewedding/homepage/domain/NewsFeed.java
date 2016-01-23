package com.pineapplewedding.homepage.domain;

import java.util.ArrayList;
import java.util.List;

public class NewsFeed {
    private String name;
    private String url;
    private List<FeedItem> feedItems;

    public NewsFeed() {
        feedItems = new ArrayList<FeedItem>();
    }

    public void addFeedItem(FeedItem feedItem) {
        if (feedItem == null) {
            throw new IllegalArgumentException("FeedItem cannot be null!");
        }
        feedItems.add(feedItem);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<FeedItem> getFeedItems() {
        return feedItems;
    }

    public void setFeedItems(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }
}
