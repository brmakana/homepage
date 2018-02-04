package io.makana.homepage.domain.news;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class NewsFeedTest {

    @Test
    public void getTimeDifference() {
        NewsFeed newsFeed = new NewsFeed();
        Date date = new Date();
        date.setTime(date.getTime() - Math.round(1000 * 60 * 60));
//        newsFeed.setPublishedDate(date);
        System.out.println(newsFeed.getTimeDifference());
    }
}