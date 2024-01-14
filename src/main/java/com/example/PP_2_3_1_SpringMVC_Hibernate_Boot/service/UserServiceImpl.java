package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.dao.RoleDao;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.dao.UserDao;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
    @Service
    @Transactional
    public class UserServiceImpl implements UserService {
        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        private final UserDao userDao;
        private final RoleDao roleDao;
        private final RoleService roleService;

        @Autowired
        public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserDao userDao, RoleDao roleDao, RoleService roleService) {
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
            this.userDao = userDao;
            this.roleDao = roleDao;
            this.roleService = roleService;
        }

        @Override
        public List<User> getUsers() {
            return userDao.getUsers();
        }

        @Transactional
        @Override
        public void saveUser(User user) {
            User newUser = new User();
            newUser.setAge(user.getAge());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassw(bCryptPasswordEncoder.encode(user.getPassword()));
            getRole(newUser);
            userDao.saveUser(newUser);
        }

        @Transactional
        @Override
        public void update(User updateUser, Long id) {
            User user_from_DB = userDao.getSingleUserById(id);
            user_from_DB.setFirstName(updateUser.getFirstName());
            user_from_DB.setLastName(updateUser.getLastName());
            user_from_DB.setAge(updateUser.getAge());
            user_from_DB.setEmail(updateUser.getEmail());
            user_from_DB.setRoles(updateUser.getRoles());
            if (updateUser.getPassword().equals("")) {
                updateUser.setPassw(getSingleUserById(updateUser.getId()).getPassword());
            } else {
                updateUser.setPassw(bCryptPasswordEncoder.encode(updateUser.getPassword()));
            }
            userDao.update(updateUser, id);
        }

        private void getRole(User user) {
            if (roleService.getAllRoles().isEmpty()) {
                user.addRole(roleDao.getRoleByName("ROLE_USER"));
            } else {
                Set<Role> roles = roleService.getAllRoles();
                for (Role roleInSet : roles) {
                    if(roleInSet == null) {
                        continue;
                    }
                    user.addRole(roleDao.getRoleByName(roleInSet.getName()));
                }
            }
        }

        @Override
        public User getSingleUserById(Long id) {
            return userDao.getSingleUserById(id);
        }

        @Override
        public User getSingleUserByLogin(String login) {
            return userDao.getSingleUserByLogin(login);
        }

        @Transactional
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
    }

