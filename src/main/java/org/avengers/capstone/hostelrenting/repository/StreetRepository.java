package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Street;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreetRepository extends JpaRepository<Street, Integer> {
    Optional<Street> findByStreetName(String streetName);
}
