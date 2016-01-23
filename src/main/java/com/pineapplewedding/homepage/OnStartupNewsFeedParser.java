package com.pineapplewedding.homepage;

import com.pineapplewedding.homepage.domain.NewsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class OnStartupNewsFeedParser {

    @Resource
    private NewsService newsService;

    @PostConstruct
    public void buildFeedsOnStartup() {
        newsService.buildNewsFeeds();
    }

    public NewsService getNewsService() {
        return newsService;
    }

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }
}
