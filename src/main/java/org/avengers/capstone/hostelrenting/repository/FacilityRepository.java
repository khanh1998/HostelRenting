package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {
    Collection<Facility> getByFacilityNameContains(String name);
    Optional<Facility> findByFacilityName(String facilityName);
}
