package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.controller;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service.RoleService;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String helloPage() {
        return "index";
    }

    @GetMapping("/user")
    public String helloPage(Model model, @AuthenticationPrincipal UserDetails curUser) {
        User user = userService.getSingleUserByLogin(curUser.getUsername());
        model.addAttribute("user", user);
        return "user/curr_user_info";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping("/login_error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
