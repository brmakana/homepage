package io.makana.homepage.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<NewsFeed> news = new ArrayList<>();
        List<String> newsFeedUrls = feedUrlRepository.getFeedUrls();
        for (String newsSite : newsFeedUrls) {
            try {
                logger.info("Fetching {}", newsSite);
                NewsFeed feed = feedFetcher.buildFeed(newsSite);
                news.add(feed);
            } catch (Exception ex) {
                logger.error("Error trying to parse feed URL [" + newsSite + "]: {} ", ex.getMessage(), ex);
            }
        }
        newsFeedRepository.setNewsFeeds(news);
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
