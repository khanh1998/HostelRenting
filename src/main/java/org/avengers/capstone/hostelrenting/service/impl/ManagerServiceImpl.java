package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.model.Manager;
import org.avengers.capstone.hostelrenting.repository.ManagerRepository;
import org.avengers.capstone.hostelrenting.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author duattt on 09/12/2020
 * @created 09/12/2020 - 11:18
 * @project youthhostelapp
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    private ManagerRepository managerRepository;

    @Autowired
    public void setManagerRepository(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public Manager findByPhone(String phone) {
        Optional<Manager> exManager = managerRepository.findByManagerPhone(phone);
        return exManager.orElse(null);
    }

    @Override
    public Manager findById(UUID id) {
        Optional<Manager> exManager = managerRepository.findById(id);
        if (exManager.isEmpty()){
            throw new EntityNotFoundException(Manager.class, "not found with", "id", id.toString());
        }
        return exManager.get();
    }

    @Override
    public Manager createNewManager(Manager manager) {
        Manager exManager = findByPhone(manager.getManagerPhone());
        if (exManager != null) {
            return exManager;
        }
        return managerRepository.save(manager);
    }

    @Override
    public Manager changeActive(Manager manager, boolean active) {
        if (manager.isActive() != active)
            manager.setActive(active);
        return manager;
    }
}
