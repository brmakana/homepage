package io.makana.homepage.domain.news;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class NewsFeed {
    public static final int DEFAULT_MAX_PER_SOURCE = 10;

    private String name;
    private String url;
    private String imageUrl;
    private List<FeedItem> feedItems = new ArrayList<>();
    private Date publishedDate;

    public NewsFeed(SyndFeed copyFrom, int maxPerSource) {
        setName(copyFrom.getTitle());
        setUrl(copyFrom.getLink());
        setPublishedDate(copyFrom.getPublishedDate());

        if (copyFrom.getImage() != null &&
                copyFrom.getImage().getUrl() != null && !copyFrom.getImage().getUrl().isEmpty()) {
            this.setImageUrl(copyFrom.getImage().getUrl());
        }

        if (copyFrom.getEntries() != null && !copyFrom.getEntries().isEmpty()) {
            int feedCount = 0;
            Iterator i = copyFrom.getEntries().iterator();
            Date mostRecentPublishedDate = null;
            while (i.hasNext() && feedCount <= maxPerSource) {
                SyndEntry entry = (SyndEntry) i.next();
                FeedItem feedItem = new FeedItem(entry);
                feedItems.add(feedItem);
                feedCount++;
                if (entry.getPublishedDate() != null) {
                    mostRecentPublishedDate = getLatestDate(mostRecentPublishedDate, entry.getPublishedDate());
                }
                if (entry.getUpdatedDate() != null) {
                    mostRecentPublishedDate = getLatestDate(mostRecentPublishedDate, entry.getUpdatedDate());
                }
            }
            setPublishedDate(getEarlistDate(mostRecentPublishedDate, getPublishedDate()));
            feedItems = feedItems.stream().distinct().collect(Collectors.toList());
        }
    }

    private Date getLatestDate(final Date first, final Date second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        }
        if (first.after(second)) {
            return first;
        } else {
            return second;
        }
    }

    private Date getEarlistDate(final Date first, final Date second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return second;
        }
        if (first.before(second)) {
            return first;
        } else {
            return second;
        }
    }

    public NewsFeed(SyndFeed copyFrom) {
        this(copyFrom, DEFAULT_MAX_PER_SOURCE);
    }

    public boolean isImageSecure() {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return true;
        } else return imageUrl.startsWith("https://");
    }

    public long getEpochPublishTime() {
        return getPublishedDate().getTime();
    }

    public Date getPublishedDate() {
        if (publishedDate != null) {
            return publishedDate;
        }
        LocalDateTime ldt = LocalDateTime.now();
        return Date.from(ldt.minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
    }

    public String getTimeDifference() {
        Date publishedDate = getPublishedDate();
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime pubDate = publishedDate.toInstant().atZone(ZoneId.systemDefault());
        Duration d = Duration.between(pubDate.toLocalDateTime(), now.toLocalDateTime());
        if (d.toHours() >= 24) {
            return d.toDays() + (d.toHours() < 48 ? " day" : " days");
        }
        else if (d.toMinutes() >= 60) {
            return d.toHours() + (d.toMinutes() < 120 ? " hour" : " hours");
        }
        else if (d.toMinutes() >= 1) {
            return d.toMinutes() + (d.toMinutes() == 1 ? " minute" : " minutes");
        }
        else {
            return Math.round(d.toMillis() / 1000) + " seconds";
        }
    }
}
