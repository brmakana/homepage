package io.makana.homepage.domain.news;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.Optional;

@Component
@Slf4j
public class FeedFetcher {

    public Optional<NewsFeed> buildFeed(String url) {
        try (
                BOMInputStream bomIn = new BOMInputStream(
                        new URL(url)
                                .openConnection()
                                .getInputStream());
                Reader reader = new StringReader(IOUtils.toString(bomIn))
        ) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(reader);

            return Optional.of(new NewsFeed(feed));
        } catch (Exception ex) {
            log.error("Exception building feed [{}]: {}", url, ex.getMessage(), ex);
            return Optional.empty();
        }
    }
}
