package com.hrs.controller;

import com.hrs.model.dto.LoginDTO;
import com.hrs.model.reponse.BookingResponse;
import com.hrs.service.AccountService;
import com.hrs.service.BookingService;
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
@RequestMapping("/masterdata/bookings")
public class BookingController {

    @Autowired
    AccountService accountService;

    @Autowired
    BookingService bookingService;

    @Autowired
    ErrorUtils errorUtils;

    @GetMapping(value = {"", "/"})
    public String viewHomePage(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }
        model.addAttribute("bookings", bookingService.getList(token));
        model.addAttribute("errors", new HashMap<>());

        return "booking";
    }

    @GetMapping(value = {"/form/{id}"})
    public String edit(Model model, HttpSession session, @PathVariable long id) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }

        BookingResponse response = bookingService.getById(token, id);
        if (response == null) {
            response = new BookingResponse();
        }
        model.addAttribute("bookingDTO", response);
        model.addAttribute("errors", new HashMap<>());
        return "booking_form";
    }

}
