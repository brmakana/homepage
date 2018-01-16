package io.makana.homepage.domain.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class FeedItem {
    private static final Logger logger = LoggerFactory.getLogger(FeedItem.class);
    private String subject;
    private String url;

    @Override
    public String toString() {
        return "FeedItem{" +
                "subject='" + subject + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthorName() {
        if (url == null || url.isEmpty()) {
            return null;
        }
        try {
            URL theUrl = new URL(url);
            return theUrl.getHost();
        } catch (MalformedURLException e) {
            logger.error("Unable to extract domain from feed url", e);
            return null;
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
