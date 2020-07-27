package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelGroupRepository extends JpaRepository<HostelGroup, Integer> {
    List<HostelGroup> findByWard_WardId(Integer districtId);

    @Query(value = "SELECT * FROM get_surroundings(?1, ?2, ?3)", nativeQuery = true)
    List<HostelGroup> getSurroundings(double latitude, double longitude, double distance);
}
