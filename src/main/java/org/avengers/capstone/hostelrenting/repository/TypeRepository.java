package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    Optional<Type> findByTypeIdAndGroup_GroupId(Integer hosteltypeId, Integer hostelGroupId);

    List<Type> findByGroup_GroupId(Integer hostelGroupId);

    /**
     * Call procedure get_surroundings
     *
     * @param latitude
     * @param longitude
     * @param distance
     * @return
     */
    @Query(value = "SELECT * FROM get_surroundings(?1, ?2, ?3)", nativeQuery = true)
    List<Type> getSurroundings(double latitude, double longitude, double distance, Pageable pageable);

    @Query(value = "SELECT * FROM get_type_by_schoolmate(?1)", nativeQuery = true)
    List<Object[]> getBySchoolMates(int schoolId);

    @Query(value = "SELECT * FROM get_type_by_compatriot(?1)", nativeQuery = true)
    List<Object[]> getByCompatriot(int provinceId);
}
