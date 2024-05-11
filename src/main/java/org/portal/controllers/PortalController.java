package org.portal.controllers;

import org.portal.models.Card;
import org.portal.models.User;
import org.portal.services.CardService;
import org.portal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PortalController {
    @Autowired
    private CardService cardService;

    @GetMapping(path = {"/portal", "/", "/index"})
    public String portal(Model model, String request) {
        List<Card> cards = cardService.findFree();

        if (request != null) {
            cards = cards.stream()
                    .filter(card -> card.getName().toLowerCase().contains(request.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("cards", cards);
        return "portal";
    }
}
