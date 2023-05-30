package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.dao;
import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("JpaQlInspection")
@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void addUser(User user) {
       entityManager.persist(user);
   }

    @Override
    public User getSingleUserById(int id) {
        TypedQuery<User> typedQuery = entityManager.createQuery
                ("select us from User us where us.id = :id", User.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        entityManager.remove(getSingleUserById(id));
    }
}

