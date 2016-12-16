package io.makana.homepage.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NewsFeedRepository {

    private Date lastUpdatedDate = new Date();
    private List<NewsFeed> feeds;
    private Object lock = new Object();

    public NewsFeedRepository() {
        feeds = new CopyOnWriteArrayList<>();
    }

    public void setNewsFeeds(List<NewsFeed> newFeeds) {
        if (newFeeds == null) {
            throw new IllegalArgumentException("argument cannot be null!");
        }
        this.feeds = newFeeds;
        this.lastUpdatedDate = new Date();

    }

    public List<NewsFeed> getNewsFeeds() {
        return feeds;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

}
