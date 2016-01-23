package com.pineapplewedding.homepage.domain;

import com.pineapplewedding.homepage.HomepageApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HomepageApplication.class)
public class NewsServiceTest {

    @Resource
    private NewsService newsService;

    @Test
    public void testBuildNewsFeeds() throws Exception {
        newsService.buildNewsFeeds();
    }
}