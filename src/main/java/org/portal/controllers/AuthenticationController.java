package org.portal.controllers;

import jakarta.validation.Valid;
import org.portal.forms.RegistrationForm;
import org.portal.models.User;
import org.portal.services.RoleService;
import org.portal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("authentication")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("login")
    public String login() {
        return "login-form";
    }

    @GetMapping("organizer/registration")
    public String organizerRegistration(Model model) {
        RegistrationForm form = new RegistrationForm();
        form.setRoleId(1);

        model.addAttribute("form", form);
        return "organizer-registration-form";
    }

    @GetMapping("participant/registration")
    public String participantRegistration(Model model) {
        RegistrationForm form = new RegistrationForm();
        form.setRoleId(2);

        model.addAttribute("form", form);
        return "participant-registration-form";
    }

    @PostMapping("registration/save")
    public String registrationSave(@Valid @ModelAttribute("form") RegistrationForm form, BindingResult result) {
        User existingUser = userService.find(form.getLogin());

        if (existingUser != null) {
            result.rejectValue("login", "1", "Login exists");
        }

        if (form.getName().equals("anonymousUser")) {
            result.rejectValue("login", "2", "Forbidden login");
        }

        if (result.hasErrors()) {
            return "redirect:/authentication/organizer/registration";
        }

        User user = new User();

        user.setRole(roleService.find(form.getRoleId()));
        user.setLogin(form.getLogin());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setName(form.getName());
        user.setSurname(form.getSurname());
        user.setAge(form.getAge());
        user.setAddress(form.getAddress());
        user.setOrganization(form.getOrganization());
        user.setResume(form.getResume());
        user.setInformation(form.getInformation());

        userService.create(user);

        return "redirect:/authentication/login";
    }
}