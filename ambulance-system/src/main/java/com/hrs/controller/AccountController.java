package com.hrs.controller;

import com.hrs.model.dto.AccountDTO;
import com.hrs.model.dto.LoginDTO;
import com.hrs.model.dto.MessageDTO;
import com.hrs.model.reponse.AccountResponse;
import com.hrs.model.reponse.RoleResponse;
import com.hrs.service.AccountService;
import com.hrs.utils.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/masterdata/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    ErrorUtils errorUtils;

    @GetMapping(value = { "", "/"})
    public String viewHomePage(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }
        List<AccountResponse> accounts =  accountService.getList(token);
        if (CollectionUtils.isEmpty(accounts)) {
            return "login";
        }
        model.addAttribute("accounts", accounts);
        model.addAttribute("errors", new HashMap<>());
        return "account";
    }

    @GetMapping(value = { "/form" })
    public String create(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }

        model.addAttribute("accountDTO", new AccountDTO());
        model.addAttribute("roles", getDataRoles());
        model.addAttribute("errors", new HashMap<>());
        return "account_form";
    }


    @GetMapping(value = { "/form/{id}" })
    public String edit(Model model, HttpSession session, @PathVariable long id, @RequestParam(required = false) String action,@RequestParam(required = false) String status) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }

        AccountResponse response = accountService.getById(token, id);
        if (response == null) {
            response = new AccountResponse();
        }
        model.addAttribute("accountDTO", response);

        model.addAttribute("roles", getDataRoles());
        model.addAttribute("errors", new HashMap<>());

        if (action == null) {
            model.addAttribute("messageDTO", null);
        } else {
            model.addAttribute("messageDTO", new MessageDTO(action, status,
                    status.equalsIgnoreCase("success") ? "Data update successful!" : "Please check the information again!"));
        }
        return "account_form";
    }

    @PostMapping(value = "/form/save")
    public String save(Model model, HttpSession session, @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        String token = (String) session.getAttribute("token");
        String redirectUrl = "/masterdata/accounts/";

        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }

        Map<String, String> errorList = new HashMap<>();
        if (bindingResult.hasErrors()) {
            errorUtils.loadErrorList(bindingResult, errorList);
            model.addAttribute("errors", errorList);
            model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                    "Please check the information again!"));
        } else {
                accountService.save(accountDTO, token);
            redirectUrl = "/masterdata/accounts/form/" + accountDTO.getId() + "?action=save&status=success";
            return "redirect:" + redirectUrl;
        }

        if (CollectionUtils.isEmpty(errorList)) {
            errorList.put("Success", "Update Complete!");
        }
        model.addAttribute("accountDTO", accountDTO); 
        model.addAttribute("roles", getDataRoles());
        model.addAttribute("errors", errorList);
        model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                "Please check the information again!"));

        return "redirect:/masterdata/accounts";
    }

    private List<RoleResponse> getDataRoles(){
        RoleResponse role = new RoleResponse();
        role.setId(1);
        role.setName("ADMIN");
        RoleResponse role2 = new RoleResponse();
        role2.setId(2);
        role2.setName("USER");
        List<RoleResponse> roles = new ArrayList<>();
        roles.add(role);
        roles.add(role2);
        return roles;
    }

}
