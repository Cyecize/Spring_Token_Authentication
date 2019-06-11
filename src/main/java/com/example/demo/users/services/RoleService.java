package com.example.demo.users.services;

import com.example.demo.users.entities.Role;
import com.example.demo.users.enums.RoleType;

import java.util.List;

public interface RoleService {

    void initialize();

    Role find(RoleType roleType);

    List<Role> findAll();
}
