package io.makana.homepage.domain.news;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class NewsFeedRepository {

    private Date lastUpdatedDate = new Date();
    private List<NewsFeed> feeds;

    public NewsFeedRepository() {
        feeds = new ArrayList<>();
    }

    public synchronized void setNewsFeeds(@NonNull List<NewsFeed> newFeeds) {
        this.feeds = newFeeds;
        this.lastUpdatedDate = new Date();
    }

    public synchronized List<NewsFeed> getNewsFeeds() {
        return feeds;
    }

    public synchronized Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

}
