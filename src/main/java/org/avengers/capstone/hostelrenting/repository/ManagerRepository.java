package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author duattt on 09/12/2020
 * @created 09/12/2020 - 11:19
 * @project youthhostelapp
 */
@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    Optional<Manager> findByManagerPhone(String phone);
}
