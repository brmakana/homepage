package io.makana.homepage.domain.news;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

@Component
public class FeedFetcher {

    public NewsFeed buildFeed(String url) throws Exception {
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        String html = IOUtils.toString(urlConnection.getInputStream());

        BOMInputStream bomIn = new BOMInputStream(IOUtils.toInputStream(html));
        String f = IOUtils.toString(bomIn);
        Reader reader = new StringReader(f);

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(reader);

        NewsFeed newsFeed = new NewsFeed(feed);
        return newsFeed;
    }

}
