package com.hrs.controller;

import com.hrs.model.dto.LoginDTO;
import com.hrs.model.dto.MessageDTO;
import com.hrs.model.dto.MessageManagerDTO;
import com.hrs.model.reponse.MessageResponse;
import com.hrs.service.MessageService;
import com.hrs.utils.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/masterdata/message")
public class MessageController {

    @Autowired
    MessageService messageManagerService;

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

        model.addAttribute("messages", messageManagerService.getList(token));
        model.addAttribute("errors", new HashMap<>());
        return "message";
    }

    @GetMapping(value = { "/form" })
    public String create(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("messageManagerDTO", new MessageManagerDTO());
            model.addAttribute("error", "error");
            return "message";
        }

        model.addAttribute("messageManagerDTO", new MessageManagerDTO());
        model.addAttribute("errors", new HashMap<>());
        return "message_form";
    }

    @GetMapping(value = {"/form/{id}"})
    public String edit(Model model, HttpSession session, @PathVariable long id, @RequestParam(required = false) String action, @RequestParam(required = false) String status) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }

        MessageResponse response = messageManagerService.getById(token, id);
        if (response == null) {
            response = new MessageResponse();
        }
        model.addAttribute("messageManagerDTO", response);
        model.addAttribute("errors", new HashMap<>());

        if (action == null) {
            model.addAttribute("messageDTO", null);
        } else {
            model.addAttribute("messageDTO", new MessageDTO(action, status,
                    status.equalsIgnoreCase("success") ? "Data update successful!" : "Please check the information again!"));
        }

        return "message_form";
    }

    @PostMapping(value = "/form/save")
    public String save(Model model, HttpSession session, @Valid MessageManagerDTO messageManagerDTO, BindingResult bindingResult) {
        String token = (String) session.getAttribute("token");
        String redirectUrl = "/masterdata/message/";
        if (token == null) {
            model.addAttribute("messageManagerDTO", new MessageManagerDTO());
            model.addAttribute("error", "error");
            return "message_form";
        }

        Map<String, String> errorList = new HashMap<>();

        if (bindingResult.hasErrors()) {
            errorUtils.loadErrorList(bindingResult, errorList);
        } else {
            MessageResponse messageManagerResponse = messageManagerService.save(messageManagerDTO, token);
            redirectUrl = "/masterdata/message/form/" + messageManagerResponse.getId() + "?action=save&status=success";
            return "redirect:" + redirectUrl;
        }

        if (CollectionUtils.isEmpty(errorList)) {
            errorList.put("Success", "Update Complete!");
        }
        model.addAttribute("messageManagerDTO", messageManagerDTO);
        model.addAttribute("errors", errorList);
        model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                "Please check the information again!"));

        return "redirect:/masterdata/message";
    }

}
