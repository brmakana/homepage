package io.makana.homepage.domain.news;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FeedItemTest {

    @Test
    public void getAuthorName() throws Exception {
        FeedItem item = new FeedItem("subject","http://www.nytimes.com");
        String expected = "www.nytimes.com";
        String actual = item.getAuthorName();
        assertEquals(expected,actual);
    }
}