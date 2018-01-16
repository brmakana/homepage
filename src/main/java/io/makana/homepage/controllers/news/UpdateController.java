package io.makana.homepage.controllers.news;

import io.makana.homepage.domain.news.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class UpdateController {

    @Resource
    private NewsService newsService;

    @ResponseBody
    @RequestMapping("/update")
    public String updateFeeds() {
        newsService.buildNewsFeeds();
        return "Update complete.";
    }
}
