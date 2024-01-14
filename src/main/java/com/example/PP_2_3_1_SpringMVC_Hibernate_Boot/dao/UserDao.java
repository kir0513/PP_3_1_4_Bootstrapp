package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.dao;



import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    void saveUser(User user);
    void update(User user, Long id);

    User getSingleUserById(Long id);

    User getSingleUserByLogin(String login);

    void deleteUser(Long id);


}
