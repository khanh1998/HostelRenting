package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.model.Role;
import org.avengers.capstone.hostelrenting.repository.RoleRepository;
import org.avengers.capstone.hostelrenting.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author duattt on 9/28/20
 * @created 28/09/2020 - 16:27
 * @project youthhostelapp
 */
@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.getOne(id);
    }
}
