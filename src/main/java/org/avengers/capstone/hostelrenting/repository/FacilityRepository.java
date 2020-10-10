package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer> {
    Collection<Facility> getByFacilityNameContains(String name);
    Facility getByFacilityName(String name);
}
