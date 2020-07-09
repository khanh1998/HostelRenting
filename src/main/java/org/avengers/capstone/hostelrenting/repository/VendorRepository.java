package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

}
