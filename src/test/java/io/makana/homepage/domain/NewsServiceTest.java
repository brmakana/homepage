package io.makana.homepage.domain;

import io.makana.homepage.HomepageApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy( {
        @ContextConfiguration(name = "parent", classes = HomepageApplication.class),
        @ContextConfiguration(name = "child", classes = NewsServiceTest.TestConfig.class)
})
public class NewsServiceTest {

    @Resource
    private NewsService newsService;

    @Resource
    private NewsFeedRepository newsFeedRepository;

    @Test
    public void testBuildNewsFeeds() throws Exception {
        newsService.buildNewsFeeds();

        List<NewsFeed> newsFeeds = newsFeedRepository.getNewsFeeds();
        NewsFeed a = new NewsFeed();
        a.setName("A");
        a.setUrl("A URL");
        assertTrue(newsFeeds.contains(a));
        assertEquals(3, newsFeeds.size());
    }

    @Test
    public void testUpdatingFeeds() throws Exception {
        newsService.buildNewsFeeds();

        List<NewsFeed> newsFeeds = newsFeedRepository.getNewsFeeds();
        assertEquals(3, newsFeeds.size());

        newsService.buildNewsFeeds();
        assertEquals(3, newsFeeds.size());
    }

    @Configuration
    public static class TestConfig {

        @Bean
        public FeedFetcher feedFetcher() throws Exception {
            FeedItem item = new FeedItem();
            item.setUrl("url");
            item.setSubject("subject");

            NewsFeed a = new NewsFeed();
            a.setName("A");
            a.setUrl("A URL");
            a.setFeedItems(Arrays.asList(item));

            NewsFeed b = new NewsFeed();
            b.setName("B");
            b.setUrl("B URL");
            b.setFeedItems(Arrays.asList(item));

            NewsFeed c = new NewsFeed();
            c.setName("C");
            c.setUrl("C URL");
            c.setFeedItems(Arrays.asList(item));


            FeedFetcher feedFetcher = mock(FeedFetcher.class);
            when(feedFetcher.buildFeed(eq("a"))).thenReturn(a);
            when(feedFetcher.buildFeed(eq("b"))).thenReturn(a);
            when(feedFetcher.buildFeed(eq("c"))).thenReturn(a);
            return feedFetcher;
        }

        @Bean
        public FeedUrlRepository feedUrlRepository() {
            FeedUrlRepository repo = mock(FeedUrlRepository.class);
            when(repo.getFeedUrls()).thenReturn(Arrays.asList("a", "b", "c"));
            return repo;
        }
    }
}