package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.StreetWard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author duattt on 9/25/20
 * @created 25/09/2020 - 15:14
 * @project youthhostelapp
 */
@Repository
public interface StreetWardRepository extends JpaRepository<StreetWard, Integer> {
    Optional<StreetWard> findByStreet_StreetIdAndWard_WardId(Integer streetId, Integer wardId);
}
