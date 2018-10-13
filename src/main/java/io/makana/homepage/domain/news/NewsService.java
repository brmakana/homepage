package io.makana.homepage.domain.news;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class NewsService {

    @Resource
    private NewsFeedRepository newsFeedRepository;

    @Resource
    private FeedFetcher feedFetcher;

    @Resource
    private FeedUrlRepository feedUrlRepository;

    @Scheduled(cron = "0 0/45 * * * *")
    public void buildNewsFeeds() {
        log.info("Updating news feeds...");
        List<NewsFeed> news = new ArrayList<>();
        List<String> newsFeedUrls = feedUrlRepository.getFeedUrls();
        if (newsFeedUrls.isEmpty()) {
            log.error("No news feeds!");
        }
        for (String newsSite : newsFeedUrls) {
            log.info("Fetching {}", newsSite);
            Optional<NewsFeed> feedAttempt = feedFetcher.buildFeed(newsSite);
            if (feedAttempt.isPresent()) {
                NewsFeed feed = feedAttempt.get();
                // @todo workaround for broken google
                if (feed != null && feed.getUrl() != null && feed.getUrl().contains("google")) {
                    feed.setUrl("https://news.google.com");
                }
                news.add(feed);
            } else {
                log.info("Feed [{}] failed", newsSite);
            }

        }
        newsFeedRepository.setNewsFeeds(news);
        log.info("Update complete");
    }

}
