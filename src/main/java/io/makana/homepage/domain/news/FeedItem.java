package io.makana.homepage.domain.news;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Getter
public class FeedItem {
    private final String subject;
    private final String url;
    private final String authorName;

    public FeedItem(String subject, String url) {
        String authorName;
        try {
            final URI theUri = new URI(url);
            authorName = theUri.getHost();
        } catch (final URISyntaxException e) {
            log.error("Unable to extract domain from feed url: {}", e.getMessage(), e);
            authorName = null;
        }
        this.authorName = authorName;
        this.subject = subject;
        this.url = url;
    }

    public FeedItem(final SyndEntry entry) {
        String authorName;
        try {
            URI theUri = new URI(entry.getLink());
            authorName = theUri.getHost();
        } catch (final URISyntaxException e) {
            log.error("Unable to extract domain from feed url: {}", e.getMessage(), e);
            authorName = null;
        }
        this.authorName = authorName;
        this.subject = buildSubject(entry);
        this.url = entry.getLink();
    }

    private String buildSubject(final SyndEntry entry) {
        final String feedText = Optional.ofNullable(entry.getTitle())
                .filter(title -> !title.isEmpty())
                .orElse(
                        Optional.ofNullable(entry.getDescription())
                                .map(SyndContent::getValue)
                                .orElse("")
                );
        if (feedText.contains("a href")) {
            return URLDecoder.decode(feedText, StandardCharsets.UTF_8);
        } else {
            return "<a class=\"feedUrl\" href=\"" + entry.getLink() + "\">" + feedText + "</a>";
        }
    }
}
