package com.pineapplewedding.homepage.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestNewsService extends NewsService {

    private static final Logger logger = LoggerFactory.getLogger(TestNewsService.class);

    @Override
    public void buildNewsFeeds() {
        logger.info("Updating test news feeds...");
        List<NewsFeed> newsFeeds = new ArrayList<NewsFeed>();
        NewsFeed newsFeed = new NewsFeed();
        FeedItem feedItem = new FeedItem();
        feedItem.setSubject("Test item");
        feedItem.setUrl("http://www.pineapplewedding.com/news.html");
        newsFeed.addFeedItem(feedItem);

        getNewsFeedRepository().setNewsFeeds(newsFeeds);
    }
}
