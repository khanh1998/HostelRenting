package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.UCategory;
import org.avengers.capstone.hostelrenting.model.Utility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, Integer> {
    @Query(value = "SELECT * FROM get_nearby_utilities(?1, ?2, ?3)", nativeQuery = true)
    List<Utility> getNearbyUtilities(Double latitude, Double longitude, Double distance);
}
