package io.makana.homepage.domain.news;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@Slf4j
public class NewsService {

    @Resource
    private NewsFeedRepository newsFeedRepository;

    @Resource
    private FeedFetcher feedFetcher;

    @Resource
    private FeedUrlRepository feedUrlRepository;

    @Resource
    private Executor threadPoolTaskExecutor;

    @Scheduled(cron = "0 0/45 * * * *")
    public void buildNewsFeeds() {
        log.info("Updating news feeds...");
        final List<NewsFeed> news = new ArrayList<>();
        final List<String> newsFeedUrls = feedUrlRepository.getFeedUrls();
        if (newsFeedUrls.isEmpty()) {
            log.error("No news feeds!");
            return;
        }
        final List<CompletableFuture<Void>> tasks = new ArrayList<>();
        for (String newsSite : newsFeedUrls) {
            log.info("Fetching {}", newsSite);
            final CompletableFuture<Void> task = CompletableFuture
                    .supplyAsync(() -> feedFetcher.buildFeed(newsSite), threadPoolTaskExecutor)
                    .thenAcceptAsync(optionalNewsFeed ->
                            optionalNewsFeed.ifPresent(newsFeed -> {
                                log.info("Successfully retrieved feed {}", newsFeed.getUrl());
                                news.add(newsFeed);
                            } ), threadPoolTaskExecutor)
                    .exceptionally(throwable -> {
                        log.error("Feed [{}] failed", newsSite, throwable);
                        return null;
                    });
            tasks.add(task);
        }
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
        newsFeedRepository.setNewsFeeds(news);
        log.info("Update complete");
    }

}
