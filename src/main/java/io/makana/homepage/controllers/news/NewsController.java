package io.makana.homepage.controllers.news;

import io.makana.homepage.domain.news.NewsFeed;
import io.makana.homepage.domain.news.NewsFeedRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequestMapping("/news**")
public class NewsController {

    public static final String DATE_FORMAT_STRING = "EEEE MMMM d, yyyy h:mm:ss a z";
    public static final String DATE_ZONE_STRING = "America/Los_Angeles";
    private final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_STRING);

    public NewsController() {
        format.setTimeZone(TimeZone.getTimeZone(DATE_ZONE_STRING));
    }

    @Resource
    private NewsFeedRepository newsFeedRepository;

    @RequestMapping
    public ModelAndView news() {
        ModelAndView mav = new ModelAndView("news");
        List<NewsFeed> feeds = newsFeedRepository.getNewsFeeds();
        mav.getModelMap().addAttribute("feeds", feeds);
        mav.getModelMap().addAttribute("date", formatDate(newsFeedRepository.getLastUpdatedDate()));
        return mav;
    }

    /**
     * returns the current time
     *
     * @return the current time as a string, formatted
     */
    private String formatDate(Date date) {
        return format.format(date);
    }
}
