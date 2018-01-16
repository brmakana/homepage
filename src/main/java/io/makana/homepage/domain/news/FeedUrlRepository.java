package io.makana.homepage.domain.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class FeedUrlRepository {

    private static final Logger logger = LoggerFactory.getLogger(FeedUrlRepository.class);
    private static String FILE_NAME = "feeds.txt";

    public List<String> getFeedUrls() {
        List<String> feedUrls = new ArrayList<String>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getFeedsAsStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    feedUrls.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return feedUrls;
    }

    private InputStream getFeedsAsStream() {
        logger.debug("Using feed file: {}", FILE_NAME);
        InputStream is =  NewsService.class.getClassLoader().getResourceAsStream(FILE_NAME);
        return is;
    }

}
