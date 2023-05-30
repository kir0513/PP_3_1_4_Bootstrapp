package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import java.util.List;

public interface UserService {
    List<User> getUsers();

    void addUser(User user);

    User getSingleUserById(int id);

    void update(User user);

    void delete(int id);
}
