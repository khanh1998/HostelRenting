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

    @Query(value = "select * from utility as u, u_type as t, u_category as c " +
            "where u.u_type_id = t.u_type_id and t.u_category_id = c.u_category_id and c.u_category_id = ?1 ;", nativeQuery = true)
    List<Utility> getByUCategoryId(Integer uCategoryId);
}
