package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.dao;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
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

    @Override
    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user);

    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getSingleUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getSingleUserByLogin(String email) {
        TypedQuery<User> typedQuery = entityManager.createQuery(
                "select user  from User user left join fetch user.roles where user.email = :email", User.class);
        typedQuery.setParameter("email", email);

        return typedQuery.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        entityManager.createQuery("delete from User where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
