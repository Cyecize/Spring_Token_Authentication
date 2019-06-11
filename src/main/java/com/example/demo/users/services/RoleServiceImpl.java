package com.example.demo.users.services;

import com.example.demo.users.entities.Role;
import com.example.demo.users.enums.RoleType;
import com.example.demo.users.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.initialize();
    }

    @Override
    public void initialize() {
        if (this.roleRepository.count() > 0) {
            return;
        }

        for (RoleType value : RoleType.values()) {
            Role role = new Role();
            role.setRole(value.name());
            this.roleRepository.save(role);
        }

        this.roleRepository.flush();
    }

    @Override
    public Role find(RoleType roleType) {
        return this.roleRepository.findByRole(roleType.name());
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }
}
