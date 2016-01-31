package com.pineapplewedding.homepage.domain;

import com.colorfulsoftware.rss.Channel;
import com.colorfulsoftware.rss.Item;
import com.colorfulsoftware.rss.RSS;
import com.colorfulsoftware.rss.RSSDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class NewsService {

    @Resource
    private NewsFeedRepository newsFeedRepository;

    public static final int MAX_PER_SOURCE = 20;
    private static String FILE_NAME = "feeds.txt";
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    private InputStream getFeedsAsStream() {
        logger.debug("Using feed file: {}", FILE_NAME);
        InputStream is =  NewsService.class.getClassLoader().getResourceAsStream(FILE_NAME);
        return is;
    }

    @Scheduled(cron = "0 0/45 * * * *")
    public void buildNewsFeeds() {
        logger.info("Updating news feeds...");
        List<NewsFeed> newsFeeds = new ArrayList<NewsFeed>();
        List<String> newsFeedUrls = getFeedUrls();
        for (String newsSite : newsFeedUrls) {
            try {
                logger.info("Parsing {}", newsSite);
                NewsFeed feed = buildFeed(newsSite);
                newsFeeds.add(feed);
            } catch (Exception ex) {
                logger.error("Error trying to parse feed URL [" + newsSite + "]: {} ", ex.getMessage(), ex);
            }
        }

        newsFeedRepository.setNewsFeeds(newsFeeds);
    }

    protected List<String> getFeedUrls() {
        List<String> feedUrls = new ArrayList<String>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getFeedsAsStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    feedUrls.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return feedUrls;
    }

    private NewsFeed buildFeed(String url) throws Exception {
        RSS rss = new RSSDoc().readRSSToBean(new URL(url));
        Channel channel = rss.getChannel();

        NewsFeed newsFeed = new NewsFeed();
        newsFeed.setName(channel.getTitle().getTitle());
        newsFeed.setUrl(channel.getLink().getLink());

        if (channel.getItems() != null) {
            int feedCount = 0;
            Iterator<Item> iterator = channel.getItems().iterator();
            while (iterator.hasNext() && feedCount <= MAX_PER_SOURCE) {
                Item item = iterator.next();
                FeedItem feedItem = new FeedItem();
                feedItem.setUrl(item.getLink().getLink());
                feedItem.setSubject(item.getTitle().getTitle());
                newsFeed.addFeedItem(feedItem);
                feedCount++;
            }
        }
        return newsFeed;
    }


    public NewsFeedRepository getNewsFeedRepository() {
        return newsFeedRepository;
    }

    public void setNewsFeedRepository(NewsFeedRepository newsFeedRepository) {
        this.newsFeedRepository = newsFeedRepository;
    }

}
