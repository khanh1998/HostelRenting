package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WardRepository extends JpaRepository<Ward, Integer> {
    Ward getByWardName(String wardName);
    Optional<Ward> findByWardIdAndDistrict_DistrictId(Integer wardId, Integer districtId);
}
