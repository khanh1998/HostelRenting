package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Manager;
import org.avengers.capstone.hostelrenting.repository.ManagerRepository;
import org.avengers.capstone.hostelrenting.service.ManagerService;
import org.avengers.capstone.hostelrenting.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Value("${manager.password.length}")
    private int managerPasswordLength;
    private Utilities utilities;
    private ManagerRepository managerRepository;
    private PasswordEncoder passwordEncoder;

    public void setManagerPasswordLength(int managerPasswordLength) {
        this.managerPasswordLength = managerPasswordLength;
    }

    @Autowired
    public void setUtilities(Utilities utilities) {
        this.utilities = utilities;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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
    public Manager createNewManager(Manager manager, String managerEmail) {
        Manager exManager = findByPhone(manager.getManagerPhone());
        if (exManager != null) {
            return exManager;
        }
        System.out.println("managerPasswordLength:" + managerPasswordLength);
        String password = utilities.getRandomString(managerPasswordLength);
        manager.setPassword(passwordEncoder.encode(password));
        String subject = "[Nhà trọ SAC] Bạn vừa tạo một quản lí mới cho khu trọ";
        String content = "Thông tin đăng nhập:\n" +
                "Số điện thoại: " + manager.getManagerPhone() + "\n" +
                "Số điện thoại: " + password + "\n";
        System.out.println(managerEmail);
        utilities.sendMailWithEmbed(subject, content, managerEmail);
        return managerRepository.save(manager);
    }

    @Override
    public Manager changeActive(Manager manager, boolean active) {
        if (manager.isActive() != active)
            manager.setActive(active);
        return manager;
    }
}
