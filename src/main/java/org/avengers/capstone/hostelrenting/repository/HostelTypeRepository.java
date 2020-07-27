package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HostelTypeRepository extends JpaRepository<HostelType, Integer> {
    Optional<HostelType> findByTypeIdAndHostelGroup_GroupId(Integer hosteltypeId, Integer hostelGroupId);

    List<HostelType> findByHostelGroup_GroupId(Integer hostelGroupId);

    @Query(value = "SELECT * FROM get_surroundings(?1, ?2, ?3)", nativeQuery = true)
    List<HostelType> getSurroundings(double latitude, double longitude, double distance);
}
