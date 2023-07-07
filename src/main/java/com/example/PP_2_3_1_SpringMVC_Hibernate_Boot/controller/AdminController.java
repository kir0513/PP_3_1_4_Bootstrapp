package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.controller;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service.RoleService;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
    public String index(ModelMap model) {
        List<User> list = userService.getUsers();
        model.addAttribute("listUsers", list);
        return "admin/list_of_users";
    }

    @GetMapping(value = "/list_users")
    public String showAllUsers(ModelMap model) {
        List<User> list = userService.getUsers();
        model.addAttribute("listUsers", list);
        return "admin/list_of_users";
    }

    @GetMapping(value = "/show_single_user")
    public String showSingleUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getSingleUserById(id));
        return "admin/show_single_user_info";
    }

    @GetMapping("/add_user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/form_add_user";
    }

    @PostMapping("addUser")
    public String createNewUser(@ModelAttribute("user") @Valid User user,
                                @RequestParam(value = "selectedRoles", required = false) String[] selectedRoles) {
        if (selectedRoles != null) {
            Set<Role> roles = new HashSet<>();
            for (String elemArrSelectedRoles : selectedRoles) {
                roles.add(roleService.getRoleByName(elemArrSelectedRoles));
            }
            user.setRoles(roles);
        }
        userService.saveUser(user);
        return "redirect:/admin";
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
        if (selectedRoles != null) {
            Set<Role> roles = new HashSet<>();
            for (String elemArrSelectedRoles : selectedRoles) {
                roles.add(roleService.getRoleByName(elemArrSelectedRoles));
            }
            user.setRoles(roles);
        }
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/delete_user")
    public String deleteUser(@RequestParam(value = "id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
