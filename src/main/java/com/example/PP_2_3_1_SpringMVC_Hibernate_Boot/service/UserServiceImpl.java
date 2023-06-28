package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service;


import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.dao.RoleDaoImpl;
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

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserDao userDao) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }


    @Override
    public void saveUser(User user) {
        RoleDaoImpl roleDao = new RoleDaoImpl();
        User newUser = new User();
        newUser.setAge(user.getAge());
        newUser.setEnabled(user.isEnabled());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        //кодируем пароль нового пользователя
        newUser.setPassw(bCryptPasswordEncoder.encode(user.getPassword()));

        System.out.println("Password newuser " + newUser.getPassw());
        //присваиваем объекту newUser роль USER если у объекта user, переданного в метод пустая роль
        //иначе перезаписываем имеющиеся в user роли в объект newUser
        if (user.getRoles().isEmpty()) {
            newUser.addRole(roleDao.getRoleByName("ROLE_USER"));
        } else {
            Set<Role> roles = user.getRoles();
            for (Role roleInSet : roles) {
                newUser.addRole(roleDao.getRoleByName(roleInSet.getName()));
            }
        }
        userDao.saveUser(newUser);
        System.out.println(newUser.getPassw());
    }

    @Override
    public void update(User user) {
//        User newUser = new User();
//        if (user.getId() == null) {
//            newUser.setPassw(bCryptPasswordEncoder.encode(user.getPassword()));
//            entityManager.persist(newUser);
//        } else {
//            newUser.setId(user.getId());
//            // если пароль пришел пустой - его не меняли получаем хеш зашифрованного пароля по id объекта user
//            // и устанавливаем его для newUser, иначе шифруем String и устанавливаем паролем получившийся хеш для newUser
//            if (user.getPassword() == null) {
//                newUser.setPassw(getSingleUserById(user.getId()).getPassword());
//            } else {
//                newUser.setPassw(bCryptPasswordEncoder.encode(user.getPassword()));
//            }
//            entityManager.merge(newUser);
//        }
        userDao.update(user);
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
}
