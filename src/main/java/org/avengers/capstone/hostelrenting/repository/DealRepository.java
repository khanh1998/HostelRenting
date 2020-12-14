package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DealRepository extends JpaRepository<Deal, Integer> {
    Optional<Deal> findByRenter_UserIdAndType_TypeIdAndStatusIs(UUID renterId, Integer typeId, Deal.STATUS status);
    @Query(value = "SELECT d.* FROM deal as d " +
            "WHERE ((d.updated_at < ?1) or (d.created_at < ?1)) " +
            "and d.status != 'CANCELLED' and d.status != 'DONE'", nativeQuery = true)
    Collection<Deal> findExpiredDealsByDayRange(Long limitTime);
}
