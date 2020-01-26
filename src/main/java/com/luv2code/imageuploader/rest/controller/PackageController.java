package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.PackageService;
import com.luv2code.imageuploader.service.UserService;
import com.luv2code.imageuploader.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

@Slf4j
@Controller
@RequestMapping("/user/package/option")
public class PackageController {

	private final PackageService packageService;

	private final UserService userService;

	public PackageController(PackageService packageService, UserService userService) {
		this.packageService = packageService;
		this.userService = userService;
	}

	@GetMapping
	private String showUserPackageOptionForm(Model model, Principal principal /*OAuth2AuthenticationToken authentication*/) {
//		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
//				authentication.getAuthorizedClientRegistrationId(),
//				authentication.getName());
//		String userInfoEndpointUri = client.getClientRegistration()
//				.getProviderDetails()
//				.getUserInfoEndpoint()
//				.getUri();
//
//		if (!StringUtils.isEmpty(userInfoEndpointUri)) {
//			RestTemplate restTemplate = new RestTemplate();
//			HttpHeaders headers = new HttpHeaders();
//			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
//			HttpEntity<String> entity = new HttpEntity<>("", headers);
//			ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
//			Map userAttributes = response.getBody();
//			if (userAttributes != null) {
//				model.addAttribute("name", userAttributes.get("name"));
//			}
//
//			log.info("Founded user: ´{}´", userAttributes.get("name"));
//			log.info("Social Sign In with: ´{}´", authentication.getAuthorizedClientRegistrationId());
//		}

		User user = userService.findByUserName(principal.getName());
		log.info("Successfully founded User with username: `{}`", user.getUserName());
		if (user.getUserPackage() != null) {
			log.info("User `{}` already has a Package.", user.getUserName());
			return "redirect:/home";
		}

		List<Package> packages = packageService.findAll();
		model.addAttribute("packages", packages);
		log.info("Successfully fetched all Packages.");

		StringBuilder freeExtensions = new StringBuilder();
		for (String extension : Utils.FREE_EXTENSION_LIST) {
			freeExtensions.append(extension).append(", ");
		}

		StringBuilder proExtensions = new StringBuilder();
		for (String extension : Utils.PRO_EXTENSION_LIST) {
			proExtensions.append(extension).append(", ");
		}

		StringBuilder goldExtensions = new StringBuilder();
		for (String extension : Utils.GOLD_EXTENSION_LIST) {
			goldExtensions.append(extension).append(", ");
		}

		model.addAttribute("freeExtensions", freeExtensions);
		model.addAttribute("proExtensions", proExtensions);
		model.addAttribute("goldExtensions", goldExtensions);

		return "user-package/user-package-form";
	}

	@GetMapping("/{packageId}")
	private String saveUserPackageOptionForm(@PathVariable Long packageId, Principal principal, Model model) {
		String username = principal.getName();
		log.info("Saving Package option for User with username: `{}`.", username);

		User user = userService.choosePackageOption(packageId, principal.getName());
		log.info("Successfully saved Package option with id `{}` to User with username `{}`.", packageId, username);
		model.addAttribute("user", user);

		return "redirect:/home";
	}
}
