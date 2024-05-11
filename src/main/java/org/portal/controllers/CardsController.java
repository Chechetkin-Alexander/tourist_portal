package org.portal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("cards")
public class CardsController {
    @GetMapping("cards")
    public String card(Model model, @RequestParam("id") String id) {
        model.addAttribute("name", id);
        return "test";
    }
    @GetMapping("create_card")
    public String createCard(Model model, @RequestParam("id") String id) {
        model.addAttribute("name", id);
        return "create-card";
    }
}
