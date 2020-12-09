package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Manager;

/**
 * @author duattt on 09/12/2020
 * @created 09/12/2020 - 11:17
 * @project youthhostelapp
 */
public interface ManagerService {
    Manager checkExistByPhone(String phone);
    Manager createNewManager(Manager manager);
}
