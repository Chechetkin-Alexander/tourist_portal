package org.portal.controllers;

import org.portal.models.User;
import org.portal.services.RoleService;
import org.portal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("authentication")
public class AuthenticationController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @GetMapping("organizer/login")
    public String organizerLogin(Model model) {
        return "organizer-login-form";
    }

    @GetMapping("participant/login")
    public String participantLogin(Model model) {
        return "organizer-login-form";
    }

    @GetMapping("organizer/registration")
    public String organizerRegistration(Model model) {
        return "organizer-registration-form";
    }

    @GetMapping("participant/registration")
    public String participantRegistration(Model model) {
        User user = new User();
        user.setRole(roleService.find(1));
        model.addAttribute("user", user);
        return "participant-registration-form";
    }
}