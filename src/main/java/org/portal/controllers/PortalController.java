package org.portal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalController {
    @RequestMapping("freelance")
    public String freelance(Model model) {
        return "freelance";
    }
}
