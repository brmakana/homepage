package com.pineapplewedding.homepage.domain;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
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
                logger.error("Error trying to parse feed URL [" + newsSite + "]: {} ", ex.getMessage());
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
        String html = IOUtils.toString(new URL(url).openConnection()
                .getInputStream());

        BOMInputStream bomIn = new BOMInputStream(IOUtils.toInputStream(html));
        String f = IOUtils.toString(bomIn);
        Reader reader = new StringReader(f);

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(reader);

        NewsFeed newsFeed = new NewsFeed();
        newsFeed.setName(feed.getTitle());
        newsFeed.setUrl(feed.getLink());

        if (feed.getEntries() != null && !feed.getEntries().isEmpty()) {
            int feedCount = 0;
            Iterator i = feed.getEntries().iterator();
            while (i.hasNext() && feedCount <= MAX_PER_SOURCE) {
                SyndEntry entry = (SyndEntry) i.next();
                FeedItem feedItem = new FeedItem();
                feedItem.setUrl(entry.getLink());
                feedItem.setSubject(entry.getTitle());
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
