package io.makana.homepage.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsService {

    @Resource
    private NewsFeedRepository newsFeedRepository;

    @Resource
    private FeedFetcher feedFetcher;

    @Resource
    private FeedUrlRepository feedUrlRepository;

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);


    @Scheduled(cron = "0 0/45 * * * *")
    public void buildNewsFeeds() {
        logger.info("Updating news feeds...");
        List<NewsFeed> newNewsFeeds = new ArrayList<NewsFeed>();
        List<String> newsFeedUrls = feedUrlRepository.getFeedUrls();
        for (String newsSite : newsFeedUrls) {
            try {
                logger.info("Parsing {}", newsSite);
                NewsFeed feed = feedFetcher.buildFeed(newsSite);
                newNewsFeeds.add(feed);
            } catch (Exception ex) {
                logger.error("Error trying to parse feed URL [" + newsSite + "]: {} ", ex.getMessage(), ex);
            }
        }
        List<NewsFeed> existingFeeds = newsFeedRepository.getNewsFeeds();
        if (newNewsFeeds != null) {
            for (NewsFeed f : existingFeeds) {
                if (!newNewsFeeds.contains(f)) {
                    newNewsFeeds.add(f);
                }
            }
        }
        newsFeedRepository.setNewsFeeds(newNewsFeeds);
    }

    public NewsFeedRepository getNewsFeedRepository() {
        return newsFeedRepository;
    }

    public void setNewsFeedRepository(NewsFeedRepository newsFeedRepository) {
        this.newsFeedRepository = newsFeedRepository;
    }

    public FeedFetcher getFeedFetcher() {
        return feedFetcher;
    }

    public void setFeedFetcher(FeedFetcher feedFetcher) {
        this.feedFetcher = feedFetcher;
    }
}
