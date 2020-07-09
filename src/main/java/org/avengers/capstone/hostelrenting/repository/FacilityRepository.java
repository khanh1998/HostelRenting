package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {
    Facility getByFacilityName(String name);
}
