package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.dao;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserDaoImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

//    @Override
//    public Set<Role> getAllRoles() {
//        return new HashSet<>(
//                entityManager.createQuery("select r from Role r", Role.class).getResultList()
//        );
//    }

//    @Override
//    public Role getRoleByName(String name){
//        TypedQuery<Role> roleName = entityManager.createQuery("select r from Role r where r.name = :name", Role.class);
//        roleName.setParameter("name", name);
//        return roleName.getResultList().stream().findFirst().orElse(null);
//    }

    @Override
    @Transactional
    public void saveUser(User user) {
        RoleDaoImpl roleDao = new RoleDaoImpl();
        User newUser = new User();
        newUser.setAge(user.getAge());
        newUser.setEnabled(user.isEnabled());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        //присваиваем объекту newUser роль USER если у объекта user, переданного в метод пустая роль
        //иначе перезаписываем имеющиеся в user роли в объект newUser
        if (user.getRoles().isEmpty()){
            newUser.addRole(roleDao.getRoleByName("ROLE_USER"));
        } else {
            Set<Role> roles = user.getRoles();
            for (Role roleInSet : roles) {
                newUser.addRole(roleDao.getRoleByName(roleInSet.getName()));
            }
        }

        // Если объект user имеет id, обновляем имеющуюся запись в БД, иначе сохраняем новую запись
        if (user.getId() == null) {
            newUser.setPassw(bCryptPasswordEncoder.encode(user.getPassword()));
            entityManager.persist(newUser);
        } else {
            newUser.setId(user.getId());
            // если пароль пришел пустой - его не меняли получаем хеш зашифрованного пароля по id объекта user
            // и устанавливаем его для newUser, иначе шифруем String и устанавливаем паролем получившийся хеш для newUser
            if (user.getPassword()==null) {
                newUser.setPassw(getSingleUserById(user.getId()).getPassword());
            } else {
                newUser.setPassw(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            entityManager.merge(newUser);
        }
    }

//    @Override
//    public User getSingleUserById(Long id) {
//        TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u where u.id = :id", User.class);
//        typedQuery.setParameter("id", id);
//        User userFromDB = typedQuery.getResultList().stream().findFirst().orElse(null);
//        //для передачи объекта пользователя во вне - затираем его пароль
//        userFromDB.setPassw(null);
//        return userFromDB;
//    }

    @Override
    public User getSingleUserById(Long id) {
        TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u where u.id = :id", User.class);
        typedQuery.setParameter("id", id);
        User user = typedQuery.getResultList().stream().findFirst().orElse(null);
        user.setPassw(null);
        return user;
    }

    @Override
    public User getSingleUserByLogin(String email) {
        TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u where u.email = :email", User.class);
        typedQuery.setParameter("email", email);
        return typedQuery.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        //entityManager.remove(getSingleUserById(id));
        entityManager.createQuery("delete from User where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
