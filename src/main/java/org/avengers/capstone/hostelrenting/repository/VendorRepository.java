package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Optional<Vendor> findByEmail(String email);
    Optional<Vendor> findByPhone(String phone);
//    Vendor findByEmail(String email);
//    Vendor findByPhone(String phone);
}
