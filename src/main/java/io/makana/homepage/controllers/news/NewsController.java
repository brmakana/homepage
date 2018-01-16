package io.makana.homepage.controllers.news;

import io.makana.homepage.domain.news.NewsFeed;
import io.makana.homepage.domain.news.NewsFeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequestMapping("/news")
public class NewsController {

    public static final String DATE_FORMAT_STRING = "EEEE MMMM d, yyyy h:mm:ss a z";
    public static final String DATE_ZONE_STRING = "America/Los_Angeles";
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Resource
    private NewsFeedRepository newsFeedRepository;

    @RequestMapping
    public String news(Model model) {
        List<NewsFeed> feeds = newsFeedRepository.getNewsFeeds();
        model.addAttribute("feeds", feeds);
        model.addAttribute("date", formatDate(newsFeedRepository.getLastUpdatedDate()));
        return "news";
    }

    /**
     * returns the current time
     *
     * @return the current time as a string, formatted
     */
    public String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_STRING);
        format.setTimeZone(TimeZone.getTimeZone(DATE_ZONE_STRING));
        return format.format(date);
    }

    public NewsFeedRepository getNewsFeedRepository() {
        return newsFeedRepository;
    }

    public void setNewsFeedRepository(NewsFeedRepository newsFeedRepository) {
        this.newsFeedRepository = newsFeedRepository;
    }
}
