package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service;


import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService  extends UserDetailsService {

    List<User> getUsers();

   // Set<Role> getAllRoles();

    void saveUser(User user);
    void update(User user);

    User getSingleUserById(Long id);

    User getSingleUserByLogin(String login);

    void deleteUser(Long id);

   // public Role getRoleByName(String name);

}
