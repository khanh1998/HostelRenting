package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {
    Collection<Facility> getByFacilityNameContains(String name);
    Facility getByFacilityName(String name);
}
