package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Manager;

import java.util.UUID;

/**
 * @author duattt on 09/12/2020
 * @created 09/12/2020 - 11:17
 * @project youthhostelapp
 */
public interface ManagerService {
    Manager findByPhone(String phone);

    Manager findById(UUID id);

    Manager createNewManager(Manager manager, String vendorEmail);

    Manager changeActive(Manager manager, boolean active);
}
