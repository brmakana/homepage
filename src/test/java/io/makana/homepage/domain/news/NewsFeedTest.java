package io.makana.homepage.domain.news;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewsFeedTest {

    private SyndFeed mockFeed = mock(SyndFeed.class);
    private Date earliest, middle, latest;
    private SyndEntry entry1, entry2, entry3;
    private NewsFeed cut;
    private static final String TITLE_1 = "Title 1";
    private static final String TITLE_2 = "Title 2";
    private static final String LINK_1 = "http://link1";
    private static final String LINK_2 = "http://link2";

    @BeforeEach
    void setup() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        earliest = calendar.getTime(); // one year ago
        calendar.add(Calendar.MONTH, 6);
        middle = calendar.getTime(); // six months ago
        calendar.setTimeInMillis(System.currentTimeMillis());
        latest = calendar.getTime(); // now-ish
        when(mockFeed.getPublishedDate()).thenReturn(latest);

        entry1 = mock(SyndEntry.class);
        when(entry1.getPublishedDate()).thenReturn(earliest);
        when(entry1.getTitle()).thenReturn(TITLE_1);
        when(entry1.getLink()).thenReturn(LINK_1);

        entry2 = mock(SyndEntry.class);
        when(entry2.getPublishedDate()).thenReturn(middle);
        when(entry2.getTitle()).thenReturn(TITLE_2);
        when(entry2.getLink()).thenReturn(LINK_2);

        entry3 = mock(SyndEntry.class);
        when(entry3.getPublishedDate()).thenReturn(latest);
        when(entry3.getTitle()).thenReturn(TITLE_1);
        when(entry3.getLink()).thenReturn(LINK_1);

        when(mockFeed.getEntries()).thenReturn(Arrays.asList(entry1, entry2, entry3));
        cut = new NewsFeed(mockFeed);
    }

    @Test
    public void usesLatestDateForPublishDate() {
        assertEquals(latest, cut.getPublishedDate());
    }

    @Test
    public void distinctFeedItems() {
        assertEquals(2, cut.getFeedItems().size());
        assertEquals(1, cut.getFeedItems().stream()
                .filter(feedItem -> feedItem.getSubject().equals(TITLE_1))
                .count());
        assertEquals(1, cut.getFeedItems().stream()
                .filter(feedItem -> feedItem.getSubject().equals(TITLE_2))
                .count());
    }
}