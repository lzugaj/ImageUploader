package com.luv2code.imageuploader.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Controller
public class HomeController {

    @GetMapping
    @RequestMapping("/")
    public String indexPage() {
        return "test";
    }
}
