package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.dao;



import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
//
    List<User> getUsers();

    Set<Role> getAllRoles();

    void saveUser(User user);

    User getSingleUserById(Long id);

    User getSingleUserByLogin(String login);

    void deleteUser(Long id);

    Role getRoleByName(String name);


}
