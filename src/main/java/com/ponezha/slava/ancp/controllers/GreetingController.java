package com.ponezha.slava.ancp.controllers;

import com.ponezha.slava.ancp.repos.OrdinsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    @Autowired
    private OrdinsRepo ordinsRepo;

    @GetMapping
    public String searchPage(String name, Model model) {
        return "search";
    }

    @GetMapping("/error")
    public String error(String name, Model model) {
        return "error";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "caseNumber", required = true, defaultValue = "") String name, Model model) {

        if (name.length() < 6) {
            model.addAttribute("message", "Please type at least six characters of your case number");
            return "search";
        }
        if (ordinsRepo.findByCaseNumber(name).size() == 0) {
            model.addAttribute("message", ":( Nothing found... Please try again later.");
            return "search";
        } else {
            model.addAttribute("message", "You case was processed :) . You can find you case in the Ordin following this link:");
            model.addAttribute("number", name);
            model.addAttribute("caseNumber", ordinsRepo.findByCaseNumber(name).get(0));
            return "search";
        }
    }
}
