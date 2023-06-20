package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.dao;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;
@Repository
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(
                entityManager.createQuery("select r from Role r", Role.class).getResultList());
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = null;
        try {
            role = entityManager
                    .createQuery("SELECT r FROM Role r WHERE r.name=:name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Роли с таким именем не существует!");
        }
        return role;
    }
}
