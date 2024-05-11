package org.portal.controllers;

import jakarta.validation.Valid;
import org.portal.forms.ProfileEditForm;
import org.portal.models.Card;
import org.portal.models.User;
import org.portal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Objects;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("profile")
    public String profile(@RequestParam(name = "id", required = false) String id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.find(authentication.getName());

        User user;
        if (id != null) {
            user = userService.find(Integer.parseInt(id));
        } else {
            user = currentUser;
        }

        List<Card> cards;
        if (Objects.equals(user.getRole().getName(), "organizer")) {
            cards = user.getOffers();
        } else {
            cards = user.getCards();
        }

        model.addAttribute("user", user);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("cards", cards);
        return "profile";
    }

    @GetMapping("profile/edit")
    public String edit(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.find(authentication.getName());

        ProfileEditForm form = new ProfileEditForm();

        form.setLogin(user.getLogin());
        form.setName(user.getName());
        form.setSurname(user.getSurname());
        form.setAge(user.getAge());
        form.setAddress(user.getAddress());
        form.setOrganization(user.getOrganization());
        form.setInformation(user.getInformation());
        form.setResume(user.getResume());
        form.setRoleName(user.getRole().getName());

        model.addAttribute("form", form);
        return "profile-edit";
    }

    @PostMapping("profile/edit/save")
    public String registrationSave(@Valid @ModelAttribute("form") ProfileEditForm form, BindingResult result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.find(authentication.getName());

        User foundedUser = userService.find(form.getLogin());

        if (foundedUser != null && foundedUser.getId() != currentUser.getId()) {
            result.rejectValue("login", "1", "Login exists");
        }

        if (form.getName().equals("anonymousUser")) {
            result.rejectValue("login", "2", "Forbidden login");
        }

        if (result.hasErrors()) {
            return "redirect:/profile";
        }

        currentUser.setName(form.getName());
        currentUser.setSurname(form.getSurname());
        currentUser.setAge(form.getAge());
        currentUser.setAddress(form.getAddress());
        currentUser.setOrganization(form.getOrganization());
        currentUser.setInformation(form.getInformation());
        currentUser.setResume(form.getResume());

        if (!Objects.equals(currentUser.getLogin(), form.getLogin())) {
            currentUser.setLogin(form.getLogin());
            userService.update(currentUser);
            return "redirect:/logout";
        }

        userService.update(currentUser);

        return "redirect:/profile";
    }
}