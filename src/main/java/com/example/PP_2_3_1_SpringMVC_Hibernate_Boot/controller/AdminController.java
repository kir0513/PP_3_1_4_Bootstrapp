package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.controller;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service.RoleService;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal UserDetails curUser) {
        List<User> list = userService.getUsers();
        User user = userService.getSingleUserByLogin(curUser.getUsername());
        model.addAttribute("listUsers", list);
        model.addAttribute("user", user);
        model.addAttribute("emptyUser", new User());
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "admin/admin_panel_2";
    }

    @GetMapping("/add_user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/form_add_user";
    }
    @PostMapping("/addUser")
    public String createNewUser(@ModelAttribute("user") @Valid User user,
                                @RequestParam(value = "selectedRoles", required = false) String[] selectedRoles) {
        if (selectedRoles != null) {
            Set<Role> roles = new HashSet<>();
            for (String elemArrSelectedRoles : selectedRoles) {
                roles.add(roleService.getRoleByName("ROLE_" + elemArrSelectedRoles));
            }
            user.setRoles(roles);
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String helloPage(Model model, @AuthenticationPrincipal UserDetails curUser) {
        User user = userService.getSingleUserByLogin(curUser.getUsername());
        model.addAttribute("user", user);
        return "user/user_panel";
    }

    @GetMapping("/form_edit_user")
    public String edit(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getSingleUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/form_edit_user";
    }

    @PatchMapping("/form_edit_user")
    public String update(@ModelAttribute("user") @Valid User user,
                         @RequestParam(value = "selectedRoles", required = false) String[] selectedRoles) {
        System.out.println(user);
        if (selectedRoles != null) {
            Set<Role> roles = new HashSet<>();
            for (String elemArrSelectedRoles : selectedRoles) {
                roles.add(roleService.getRoleByName("ROLE_" + elemArrSelectedRoles));
            }
            user.setRoles(roles);
        }
        System.out.println(user);
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping(value = "/delete_user")
    public String deleteUser(@RequestParam(value = "id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
