package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.ImageFormat;
import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.PackageService;
import com.luv2code.imageuploader.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by lzugaj on Monday, December 2019
 */

@Slf4j
@Controller
@RequestMapping("/user/package/tracker")
public class PackageTrackerController {

    private final UserService userService;

    private final PackageService packageService;

    @Autowired
    public PackageTrackerController(UserService userService, PackageService packageService) {
        this.userService = userService;
        this.packageService = packageService;
    }

    @GetMapping("/{username}")
    private String showUserPackageTracker(@PathVariable String username, Model model) {
        User searchedUser = userService.findByUserName(username);
        log.info("Successfully founded User with username: `{}`", searchedUser.getUserName());
        model.addAttribute("user", searchedUser);

        Package userPackage = packageService.findById(searchedUser.getUserPackage().getId());
        log.info("Successfully founded Package(´{}´) for User with username: `{}`", userPackage.getName(), searchedUser.getUserName());
        model.addAttribute("package", userPackage);

        BigDecimal currentUploadSizeOfImages = BigDecimal.valueOf(searchedUser.getUploadedImageSizeWithCurrentPackage()).divide(BigDecimal.valueOf(1000000), 3, RoundingMode.CEILING);
        model.addAttribute("currentUploadSizeOfImages", currentUploadSizeOfImages);

        List<ImageFormat> packageExtensions = userPackage.getImageFormats();
        log.info("Successfully founded all ImageFormat extensions for User with username: `{}`", username);

        StringBuilder extensions = new StringBuilder();
        for (ImageFormat imageFormat : packageExtensions) {
            extensions.append(imageFormat.getExtensionName()).append(", ");
        }

        model.addAttribute("packageExtensions", extensions);

        return "package-tracker/package-tracker-info";
    }
}
