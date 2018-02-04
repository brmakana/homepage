package io.makana.homepage.domain.news;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@Data
public class FeedItem {
    private static final Logger logger = LoggerFactory.getLogger(FeedItem.class);
    private String subject;
    private String url;
    private Date publishedDate;

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
}
