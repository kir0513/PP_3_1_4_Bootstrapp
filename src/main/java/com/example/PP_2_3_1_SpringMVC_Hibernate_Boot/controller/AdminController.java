package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.controller;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service.RoleService;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }
    @GetMapping(value = "/admin")
    public String index(ModelMap model) {
        List<User> list = userService.getUsers();
        model.addAttribute("listUsers", list);
        System.out.println("Переход по / на /index.html");
        return "admin/list_of_users";
    }
    @GetMapping(value = "/admin/list_users")
    public String showAllUsers(ModelMap model) {
        List<User> list = userService.getUsers();
        model.addAttribute("listUsers", list);
        System.out.println("Открытие /admin/list_users (pages/list_of_users.html)");
        return "admin/list_of_users";
    }
    @GetMapping(value = "/admin/show_single_user")
    public String showSingleUser (@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getSingleUserById(id));
        return "admin/show_single_user_info";
    }
    @GetMapping("/admin/add_user")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/form_add_user";
    }
    @PostMapping("/admin/save_or_update_user")
    public String saveNewOrUpdateExistUser(@ModelAttribute("user") User user,
                                           @RequestParam(value = "selectedRoles", required = false) String[] selectedRoles){
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
    @GetMapping("/admin/edit_user")
    public String edit(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getSingleUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/form_edit_user";
    }

    @GetMapping(value = "/admin/delete_user")
    public String deleteUser (@RequestParam(value = "id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
