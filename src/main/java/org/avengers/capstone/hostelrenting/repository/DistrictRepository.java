package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    District getByDistrictName(String districtName);
}
