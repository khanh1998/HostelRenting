package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    Optional<District> getByDistrictName(String districtName);
    Optional<District> findByDistrictIdAndProvince_ProvinceId(Integer districtId, Integer provinceId);
}
