package com.hrs.controller;

import com.hrs.model.dto.LoginDTO;
import com.hrs.model.reponse.PharmacyResponse;
import com.hrs.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("/masterdata/pharmacy")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @GetMapping(value = { "", "/"})
    public String viewHomePage(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }
        model.addAttribute("pharmacy", pharmacyService.getList(token));
        model.addAttribute("errors", new HashMap<>());
        return "pharmacy";
    }

    @GetMapping(value = { "/form/{id}"})
    public String edit(Model model, HttpSession session, @PathVariable long id) {
        String token = (String) session.getAttribute("token");

        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }

        model.addAttribute("pharmacyDTO", pharmacyService.getById(token, id));
        model.addAttribute("errors", new HashMap<>());
        return "pharmacy_form";
    }

}
