package io.makana.homepage.domain.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
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
    @Async("threadPoolTaskExecutor")
    public void buildNewsFeeds() {
        logger.info("Updating news feeds...");
        List<NewsFeed> news = new ArrayList<>();
        List<String> newsFeedUrls = feedUrlRepository.getFeedUrls();
        for (String newsSite : newsFeedUrls) {
            try {
                logger.info("Fetching {}", newsSite);
                NewsFeed feed = feedFetcher.buildFeed(newsSite);
                // @todo workaround for broken google
                if (feed != null && feed.getUrl() != null && feed.getUrl().contains("google")) {
                    feed.setUrl("https://news.google.com");
                }
                news.add(feed);
            } catch (Exception ex) {
                logger.error("Error trying to parse feed URL [" + newsSite + "]: {} ", ex.getMessage(), ex);
            }
        }
        newsFeedRepository.setNewsFeeds(news);
        logger.info("Update complete");
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
