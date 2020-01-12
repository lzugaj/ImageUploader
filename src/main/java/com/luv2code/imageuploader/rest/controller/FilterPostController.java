package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserService;
import com.luv2code.imageuploader.utils.MessageError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by lzugaj on Tuesday, December 2019
 */

@Slf4j
@Controller
@RequestMapping("/filter")
public class FilterPostController {

    private final PostService postService;

    private final UserService userService;

    @Autowired
    public FilterPostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
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
            redirectAttributes.addFlashAttribute("errorMessage", MessageError.PLEASE_FILL_ALL_REQUIRED_FIELDS);
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

        log.info("Successfully founded all filtered Posts.");
        model.addAttribute("posts", posts);

        Map<Long, String> postImages = postService.mapAllPostImages(posts);
        log.info("Successfully mapped all Post images.");
        model.addAttribute("postImages", postImages);

        Map<Long, String> hashTags = postService.mapHashTags(posts);
        log.info("Successfully mapped all HashTag for Post images.");
        model.addAttribute("hashTags", hashTags);

        Map<Long, Integer> daysOfMonth = postService.mapDateOfAllPosts(posts);
        log.info("Successfully mapped all day of month foreach Post.");
        model.addAttribute("daysOfMonth", daysOfMonth);

        List<User> users = userService.findAll();
        log.info("Successfully founded all Users.");
        model.addAttribute("users", users);

        Map<Long, String> userProfileImages = userService.mapAllProfileImages(users);
        log.info("Successfully mapped all UserProfile images.");
        model.addAttribute("userProfileImages", userProfileImages);

        // TODO: Delete?
        Map<Long, User> usersMap = userService.mapAllUsers(users);
        log.info("Successfully mapped all Users.");
        model.addAttribute("usersMap", usersMap);

        // TODO: Delete?
        Map<Long, Post> postsMap = postService.mapAllPosts(posts);
        log.info("Successfully mapped all UserProfile images.");
        model.addAttribute("postsMap", postsMap);

        Map<Long, Integer> numberOfPostCommentsMap = postService.numberOfPostComments(posts);
        log.info("Successfully mapped number of Comments for Post.");
        model.addAttribute("numberOfPostCommentsMap", numberOfPostCommentsMap);

        return "home/index";
    }
}
