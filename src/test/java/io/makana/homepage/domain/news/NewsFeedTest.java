package io.makana.homepage.domain.news;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewsFeedTest {

    @Test
    public void usesLatestDate() {
        final SyndFeed mockFeed = mock(SyndFeed.class);
        final Calendar calendar = Calendar.getInstance();
        final Date earliest, middle, latest;
        calendar.add(Calendar.YEAR, -1);
        earliest = calendar.getTime(); // one year ago
        calendar.add(Calendar.MONTH, 6);
        middle = calendar.getTime(); // six months ago
        calendar.setTimeInMillis(System.currentTimeMillis());
        latest = calendar.getTime(); // now-ish
        when(mockFeed.getPublishedDate()).thenReturn(latest);

        final SyndEntry entry1 = mock(SyndEntry.class);
        when(entry1.getPublishedDate()).thenReturn(earliest);
        final SyndEntry entry2 = mock(SyndEntry.class);
        when(entry2.getPublishedDate()).thenReturn(middle);
        when(mockFeed.getEntries()).thenReturn(Arrays.asList(entry1, entry2));

        final NewsFeed cut = new NewsFeed(mockFeed);
        assertEquals(middle, cut.getPublishedDate());
    }
}