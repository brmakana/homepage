package io.makana.homepage.domain.news;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;

@Value
@Slf4j
public class FeedItem {
    private String subject;
    private String url;
    private String authorName;

    public FeedItem(String subject, String url) {
        String authorName;
        try {
            URL theUrl = new URL(url);
            authorName = theUrl.getHost();
        } catch (MalformedURLException e) {
            log.error("Unable to extract domain from feed url", e);
            authorName = null;
        }
        this.authorName = authorName;
        this.subject = subject;
        this.url = url;
    }
}
