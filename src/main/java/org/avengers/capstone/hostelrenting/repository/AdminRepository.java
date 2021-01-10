package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByPhone(String phone);
}
