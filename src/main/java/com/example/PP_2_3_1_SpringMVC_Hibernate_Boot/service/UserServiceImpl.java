package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service;


import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.dao.UserDao;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

//    @Override
//    public Set<Role> getAllRoles() {
//        return userDao.getAllRoles();
//    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User getSingleUserById(Long id) {
        return userDao.getSingleUserById(id);
    }

    @Override
    public User getSingleUserByLogin(String login) {
        return userDao.getSingleUserByLogin(login);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.getSingleUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с таким логином не найден");
        }
        return user;
    }

//    @Override
//    public Role getRoleByName(String name) {
//        return userDao.getRoleByName(name);
//    }
}
