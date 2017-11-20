package io.makana.homepage.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class FeedItemTest {
    @Test
    public void getAuthorName() throws Exception {
        FeedItem item = new FeedItem();
        item.setUrl("http://www.nytimes.com");
        String expected = "www.nytimes.com";
        String actual = item.getAuthorName();
        assertEquals(expected,actual);
    }

}