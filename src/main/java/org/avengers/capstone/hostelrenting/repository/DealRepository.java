package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Integer> {
    Optional<Deal> findByRenter_UserIdAndType_TypeIdAndStatusIs(Long renterId, Integer typeId, Deal.STATUS status);
}
