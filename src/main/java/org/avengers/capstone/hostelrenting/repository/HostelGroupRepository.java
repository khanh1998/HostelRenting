package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelGroupRepository extends JpaRepository<HostelGroup, Integer> {
    List<HostelGroup> findByDistrict_DistrictId(Integer districtId);
    HostelGroup findByLongitudeAndLatitude(String longitude, String latitude);
}
