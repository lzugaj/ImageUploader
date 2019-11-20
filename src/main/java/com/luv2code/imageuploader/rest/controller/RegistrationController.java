package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.dto.UserDto;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

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

        userService.save(userDto);
        log.info("Successfully created new user with username: ´{}´", userDto.getUserName());
        redirectAttributes.addFlashAttribute("success", "Thank you " + userDto.getFirstName() + "!" + " You have successfully registered! :)");
        return "redirect:/login"; // TODO: Redirect to page where User will choose Package Option!!!
    }
}
