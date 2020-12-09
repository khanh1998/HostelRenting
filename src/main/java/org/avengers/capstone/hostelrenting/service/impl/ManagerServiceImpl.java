package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.model.Manager;
import org.avengers.capstone.hostelrenting.repository.ManagerRepository;
import org.avengers.capstone.hostelrenting.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author duattt on 09/12/2020
 * @created 09/12/2020 - 11:18
 * @project youthhostelapp
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Manager checkExistByPhone(String phone) {
        Optional<Manager> exManager = managerRepository.findByManagerPhone(phone);
        if (exManager.isPresent())
            return exManager.get();
        else
            return null;
    }

    @Override
    public Manager createNewManager(Manager manager) {
        Manager exManager = checkExistByPhone(manager.getManagerPhone());
        if (exManager != null) {
            return exManager;
        }
        return managerRepository.save(manager);
    }
}
