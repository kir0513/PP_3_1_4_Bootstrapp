package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.controller;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/")
public class UserController {

    //внедряем зависимость userService в контроллер через конструктор

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //метод возвращающий список пользователей. Здесь получаем всех людей из DAO и передаем их на отображение
    @GetMapping()
    public String index(Model model) {
        // model.addAttribute("user", userDaoImp.index());
        List<User> list = userService.getUsers();
        model.addAttribute("listUsers", list);
        return "user/index";
    }

    //поиск пользователя по id из DAO и передаем на представление
    @GetMapping("user/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getSingleUserById(id));
        return "user/show";
    }

    //метод, возвращающий html форму для создания нового пользователя
    @GetMapping("addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user/addUser";
    }

    //метод, принимающий Post запрос создающий пользователя и добавляющий его в БД
    @PostMapping("addUser")
    public String createNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/addUser";
        } else {
            userService.addUser(user);
            return "redirect:/";
        }
    }

    @GetMapping("/user/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getSingleUserById(id));
        return "user/edit";
    }

    @PatchMapping("/user/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "user/edit";
        } else {
            userService.update(user);
            return "redirect:/";
        }
    }

    @DeleteMapping("user/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/";
    }
}
