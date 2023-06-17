package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.controller;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String helloPage() {
        System.out.println("asdadasdadasdadadadad");
        return "index";
    }
    @GetMapping("/user")
    public String helloPage(Model model, @AuthenticationPrincipal UserDetails curUser) {
        User user = userService.getSingleUserByLogin(curUser.getUsername());
        model.addAttribute("user", user);
        return "user/curr_user_info";
    }
    @GetMapping(value = "/admin")
    public String index(ModelMap model) {
        List<User> list = userService.getUsers();
        model.addAttribute("listUsers", list);
        System.out.println("Переход по / на /index.html");
        return "admin/list_of_users";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        System.out.println("Переход по ссылке /login на /login.html");
        return "login";
    }

    @RequestMapping("/login_error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        System.out.println("Переход по ссылке /login_error на /login.html с сообщением \"loginError\"");
        return "login";
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
        model.addAttribute("roles", userService.getAllRoles());
        return "admin/form_add_user";
    }
    @PostMapping("/admin/save_or_update_user")
    public String saveNewOrUpdateExistUser(@ModelAttribute("user") User user,
                                @RequestParam(value = "selectedRoles", required = false) String[] selectedRoles
                                 ){
        if (selectedRoles != null) {
            Set<Role> roles = new HashSet<>();
            for (String elemArrSelectedRoles : selectedRoles) {
                roles.add(userService.getRoleByName(elemArrSelectedRoles));
            }
            user.setRoles(roles);
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit_user")
    public String edit(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getSingleUserById(id));
        model.addAttribute("roles", userService.getAllRoles());
        return "admin/form_edit_user";
    }

    @GetMapping(value = "/admin/delete_user")
    public String deleteUser (@RequestParam(value = "id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
