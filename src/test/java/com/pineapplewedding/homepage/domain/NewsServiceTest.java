package com.pineapplewedding.homepage.domain;

import com.pineapplewedding.homepage.HomepageApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy( {
        @ContextConfiguration(name = "parent", classes = HomepageApplication.class),
        @ContextConfiguration(name = "child", classes = NewsServiceTest.TestConfig.class)
})
public class NewsServiceTest {

    @Resource
    private NewsService newsService;

    @Test
    public void testBuildNewsFeeds() throws Exception {
        newsService.buildNewsFeeds();
    }

    @Configuration
    public static class TestConfig {
        @Bean
        public NewsService newsService() {
            return new TestNewsService();
        }
    }
}