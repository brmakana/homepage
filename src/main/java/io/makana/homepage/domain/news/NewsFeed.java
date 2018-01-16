package io.makana.homepage.domain.news;

import java.util.ArrayList;
import java.util.List;

public class NewsFeed {
    private String name;
    private String url;
    private String imageUrl;
    private List<FeedItem> feedItems;

    public NewsFeed() {
        feedItems = new ArrayList<FeedItem>();
    }

    public void addFeedItem(FeedItem feedItem) {
        if (feedItem == null) {
            throw new IllegalArgumentException("FeedItem cannot be null!");
        }
        feedItems.add(feedItem);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<FeedItem> getFeedItems() {
        return feedItems;
    }

    public void setFeedItems(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsFeed newsFeed = (NewsFeed) o;

        if (getName() != null ? !getName().equals(newsFeed.getName()) : newsFeed.getName() != null) return false;
        return getUrl() != null ? getUrl().equals(newsFeed.getUrl()) : newsFeed.getUrl() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        return result;
    }
}
