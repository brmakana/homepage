package io.makana.homepage.domain.news;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FeedUrlRepository implements InitializingBean {

    @Value("${feeds.file:classpath:feeds.txt}")
    private Resource feedFile;
    private List<String> feedUrls;

    public List<String> getFeedUrls() {
        return feedUrls;
    }

    private synchronized void parseFeedUrls() {
        List<String> feedUrls = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(feedFile.getInputStream()))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    feedUrls.add(line);
                }
            }
        } catch (Exception e) {
            log.error("Exception retrieving feed urls from file: {}", e.getMessage(), e);
        }
        this.feedUrls = feedUrls;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        parseFeedUrls();
    }
}
