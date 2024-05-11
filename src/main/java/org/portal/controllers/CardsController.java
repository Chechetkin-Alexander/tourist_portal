package org.portal.controllers;

import jakarta.validation.Valid;
import org.portal.forms.CardForm;
import org.portal.models.Card;
import org.portal.models.User;
import org.portal.schedulers.CardsScheduler;
import org.portal.services.CardService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Calendar;

@Controller
@RequestMapping("cards")
public class CardsController {
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;

    @GetMapping("card")
    public String card(@RequestParam("id") String id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.find(authentication.getName());

        Card card = cardService.find(Integer.parseInt(id));

        Integer daysBeforeExpiration = CardsScheduler.getDaysRemaining(card);

        model.addAttribute("user", user);
        model.addAttribute("card", card);
        model.addAttribute("daysBeforeExpiration", daysBeforeExpiration);
        return "card";
    }

    @GetMapping("create-card")
    public String createCard(Model model) {
        model.addAttribute("form", new CardForm());
        return "create-card";
    }

    @PostMapping("save")
    public String saveCard(@Valid @ModelAttribute("form") CardForm form, BindingResult result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.find(authentication.getName());

        if (result.hasErrors()) {
            return "create-card";
        }

        Card card = new Card();

        card.setName(form.getName());
        card.setOrganizer(user);
        card.setPrice(form.getPrice());
        card.setDescription(form.getDescription());

        cardService.create(card);

        return "redirect:/profile";
    }

    @GetMapping("delete")
    public String deleteCard(@RequestParam("id") String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.find(authentication.getName());

        Card card = cardService.find(Integer.parseInt(id));

        if (user.getId() != card.getParticipant().getId()) {
            return "redirect:/portal";
        }

        cardService.delete(card);

        return "redirect:/profile";
    }

    @GetMapping("accept")
    public String acceptCard(@RequestParam("id") String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.find(authentication.getName());

        Card card = cardService.find(Integer.parseInt(id));

        if (card.getParticipant() != null) {
            return "redirect:/portal";
        }

        card.setParticipant(user);
        cardService.update(card);

        return "redirect:/cards/card?id=" + id;
    }

    @GetMapping("complete")
    public String completeCard(@RequestParam("id") String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.find(authentication.getName());

        Card card = cardService.find(Integer.parseInt(id));

        if (card.getParticipant() == null || user.getId() != card.getParticipant().getId()) {
            return "redirect:/portal";
        }

        card.setCompleteDate(Calendar.getInstance().getTime());
        cardService.update(card);

        return "redirect:/cards/card?id=" + id;
    }
}