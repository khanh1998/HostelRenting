package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepository extends JpaRepository<Renter, String> {
    Renter findByUsername(String username);
}
