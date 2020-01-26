package com.luv2code.imageuploader.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Controller
public class LoginController {

//    private static final String authorizationRequestBaseUri = "oauth2/authorize-client";
//
//    private Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
//
//    @Autowired
//    private ClientRegistrationRepository clientRegistrationRepository;
//
//    @Autowired
//    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/login")
    public String showLoginForm() {
//        Iterable<ClientRegistration> clientRegistrations = null;
//        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
//        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
//            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
//        }
//
//        if (clientRegistrations != null) {
//            clientRegistrations.forEach(registration ->
//                    oauth2AuthenticationUrls.put(
//                            registration.getClientName(),
//                            authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
//            LOGGER.info("clientRegistrations: ´{}´", clientRegistrations.toString());
//        }
//
//        model.addAttribute("googleUrl", oauth2AuthenticationUrls.values().toArray()[0].toString());
//        log.info("Google url: ´{}´", oauth2AuthenticationUrls.values().toArray()[0].toString());
//        model.addAttribute("githubUrl", oauth2AuthenticationUrls.values().toArray()[1].toString());
//        log.info("Github url: ´{}´", oauth2AuthenticationUrls.values().toArray()[1].toString());

        log.info("Uploading Login Form");
        return "login/login-form";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

}
