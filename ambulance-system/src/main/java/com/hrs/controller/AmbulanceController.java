package com.hrs.controller;

import com.hrs.model.reponse.AmbulanceResponse;
import com.hrs.service.AmbulanceService;
import com.hrs.utils.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("/masterdata/ambulance")
public class AmbulanceController {

    @Autowired
    AmbulanceService ambulanceService;

    @Autowired
    ErrorUtils errorUtils;

    @GetMapping(value = {"", "/"})
    public String viewHomePage(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
//        if (token == null) {
//            model.addAttribute("loginDTO", new LoginDTO());
//            model.addAttribute("error", "error");
//            return "login";
//        }

        model.addAttribute("ambulances", ambulanceService.getList(token));
        model.addAttribute("errors", new HashMap<>());
        return "ambulance";
    }

    @GetMapping(value = {"/form/{id}"})
    public String edit(Model model, HttpSession session, @PathVariable long id) {
        String token = (String) session.getAttribute("token");
//        if (token == null) {
//            model.addAttribute("loginDTO", new LoginDTO());
//            model.addAttribute("error", "error");
//            return "login";
//        }

        AmbulanceResponse response = ambulanceService.getById(token, id);
        if (response == null) {
            response = new AmbulanceResponse();
        }
        model.addAttribute("ambulanceDTO", response);

//        model.addAttribute("roles", getDataRoles());
        model.addAttribute("errors", new HashMap<>());
        return "ambulance_form";
    }

}
