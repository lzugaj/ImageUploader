package com.luv2code.imageuploader.rest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.service.PostService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Tuesday, December 2019
 */

@Slf4j
@Controller
@RequestMapping("/filter")
public class FilterPostController {

    private final PostService postService;

    @Autowired
    public FilterPostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public String submitFilterPostByCriteriaForm(Model model,
            @RequestParam(value = "hashTag", required = false) String hashTag,
            @RequestParam(value = "sizeFrom", required = false) Double sizeFrom,
            @RequestParam(value = "sizeTo") Double sizeTo,
            @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @RequestParam(value = "dateTo") String dateTo,
            @RequestParam(value = "author", required = false) String author,
            RedirectAttributes redirectAttributes) throws ParseException {
        if (hashTag.isEmpty() || sizeFrom == null || dateFrom.equals("") || author.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please fill all fields.");
            return "redirect:/home";
        }

        log.info("Filter all Posts by some criteria.");
        List<Post> posts;
        if (sizeTo == null && dateTo.equals("")) {
            posts = postService.filterPostsByMandatoryCriteria(hashTag, sizeFrom, new SimpleDateFormat("yyyy-mm-dd").parse(dateFrom), author);
            log.info("Successfully filtered Posts by mandatory criteria.");
        } else if (sizeTo != null && dateTo.equals("")) {
            posts = postService.filterPostsByMandatoryAndSizeToCriteria(hashTag, sizeFrom, sizeTo, new SimpleDateFormat("yyyy-mm-dd").parse(dateFrom), author);
            log.info("Successfully filtered Posts by mandatory criteria and size to.");
        } else if (sizeTo == null) {
            posts = postService.filterPostsByMandatoryAndDateToCriteria(hashTag, sizeFrom, new SimpleDateFormat("yyyy-mm-dd").parse(dateFrom),
                    new SimpleDateFormat("yyyy-mm-dd").parse(dateTo), author);
            log.info("Successfully filtered Posts by mandatory criteria and date to.");
        } else {
            posts = postService.filterPostsByAllCriteria(hashTag, sizeFrom, sizeTo, new SimpleDateFormat("yyyy-mm-dd").parse(dateFrom),
                    new SimpleDateFormat("yyyy-mm-dd").parse(dateTo), author);
            log.info("Successfully filtered Posts by all criteria.");
        }

        log.info("Successfully founded filtered Posts.");
        model.addAttribute("posts", posts);
        return "redirect:/home";
    }
}
