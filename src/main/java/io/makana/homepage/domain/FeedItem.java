package io.makana.homepage.domain;

public class FeedItem {
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

    public void setUrl(String url) {
        this.url = url;
    }
}
