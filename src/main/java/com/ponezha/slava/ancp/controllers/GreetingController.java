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
    public String greeting(String name, Model model) {
        return "search";
    }

    @GetMapping("search")
    public String showOrdins(@RequestParam(name = "caseNumber", required = true, defaultValue = "") String name, Model model) {

        if (name.length() < 6) {
            model.addAttribute("errorMessage", "Please type at least six characters of your case number");
            return "search";
        }
        if (ordinsRepo.findByCaseNumbersOrMe(name).size() == 0) {
            model.addAttribute("errorMessage", ":( Nothing found... Please try again later.");
            return "search";
        } else {
            model.addAttribute("errorMessage", "");
            model.addAttribute("number", name);
            model.addAttribute("caseNumbers", ordinsRepo.findByCaseNumbersOrMe(name));
            return "search";
        }
    }
}
