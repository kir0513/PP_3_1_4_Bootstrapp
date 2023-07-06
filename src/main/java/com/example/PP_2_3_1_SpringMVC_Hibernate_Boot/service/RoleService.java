package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.service;

import com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getAllRoles();
    public Role getRoleByName(String name);

   public void addRole(String role);
}
