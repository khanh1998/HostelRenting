package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {
    Ward getByWardName(String wardName);
//    Optional<Ward> findByWardIdAndDistrict_DistrictId(Integer wardId, Integer districtId);
    Ward findByWardIdAndDistrict_DistrictId(Integer wardId, Integer districtId);
}
