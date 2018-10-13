package io.makana.homepage.domain.news;

import io.makana.homepage.domain.news.FeedItem;
import org.junit.Test;

import static org.junit.Assert.*;

public class FeedItemTest {
    @Test
    public void getAuthorName() throws Exception {
        FeedItem item = new FeedItem("subject","http://www.nytimes.com");
        String expected = "www.nytimes.com";
        String actual = item.getAuthorName();
        assertEquals(expected,actual);
    }
}