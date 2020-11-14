package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.TypeFacility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * @author duattt on 11/14/20
 * @created 14/11/2020 - 10:14
 * @project youthhostelapp
 */
public interface TypeFacilityRepository extends JpaRepository<TypeFacility, Integer> {
    Collection<TypeFacility> findByType_TypeIdAndFacility_IsApproved(Integer typeId, boolean isApproved);
}
