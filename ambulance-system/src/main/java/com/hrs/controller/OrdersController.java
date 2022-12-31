package com.hrs.controller;

import com.hrs.model.dto.LoginDTO;
import com.hrs.service.OrderDetailsService;
import com.hrs.service.OrdersService;
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
@RequestMapping("/masterdata/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailsService orderDetailsService;

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
        model.addAttribute("ordersList", ordersService.getList(token));
        model.addAttribute("errors", new HashMap<>());
        return "orders";
    }


    @GetMapping(value = {"/form/{id}"})
    public String edit(Model model, HttpSession session, @PathVariable long id) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }

        model.addAttribute("orderDetails", orderDetailsService.getListByOrder(token, id));
        model.addAttribute("ordersDTO", ordersService.getById(token, id));
        model.addAttribute("errors", new HashMap<>());
        return "orders_form";
    }

}
