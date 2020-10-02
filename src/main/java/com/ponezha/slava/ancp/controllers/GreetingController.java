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
            model.addAttribute("message", "Пожалуйста, введите минимум 6 цифр номера досара в указанном в примере формате");
            return "search";
        }
        if (ordinsRepo.findByCaseNumber(name).size() == 0) {
            model.addAttribute("message", "Ваш номер досара не найден в базе данных приказов. Попробуйте позже. Мы обновляем базу данных каждый день.");
            return "search";
        } else {
            model.addAttribute("message", "Ваш досар найден в приказе:");
            model.addAttribute("caseNumber", ordinsRepo.findByCaseNumber(name).get(0));
            return "search";
        }
    }
}
