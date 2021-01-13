package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, UUID> {
    Optional<Vendor> findByPhone(String phone);
    int countByPhone(String phone);
}
