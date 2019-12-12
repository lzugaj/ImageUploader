package com.luv2code.imageuploader.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.imageuploader.dto.UserDto;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        log.info("Uploading Registration Form");
        return "registration/registration-form";
    }

    @PostMapping("/registration")
    public String processRegistrationForm(@Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        String userName = userDto.getUserName();
        log.info("Signing Up User with username: ´{}´", userName);
        if (bindingResult.hasErrors()) {
            log.info("Error! Something went wrong while Signing Up User with username: ´{}´", userName);
            return "registration/registration-form";
        }

        User existing = userService.findByUserName(userName);
        if (existing != null) {
            model.addAttribute("userDto", new UserDto());
            model.addAttribute("registrationError", "User name already exists. Please enter new user name.");
            log.info("User name, ´{}´, already exists.", userName);
            return "registration/registration-form";
        }

        User user = userService.save(userDto);
        log.info("Successfully created new user with username: ´{}´", user.getUserName());
        redirectAttributes.addFlashAttribute("successfulRegistrationMessage", "Thank you " + user.getFirstName() + "!" + " You have successfully registered! Login now! :)");
        return "redirect:/login";
    }
}
