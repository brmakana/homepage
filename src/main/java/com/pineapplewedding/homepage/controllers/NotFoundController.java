package com.pineapplewedding.homepage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class NotFoundController {

        @ExceptionHandler
        public String index() {
            return "error";
        }
}
