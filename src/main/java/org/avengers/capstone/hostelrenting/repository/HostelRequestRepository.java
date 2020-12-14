package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 21:08
 * @project youthhostelapp
 */
@Repository
public interface HostelRequestRepository extends JpaRepository<HostelRequest, Integer> {
    List<HostelRequest> findByRenter_UserId(UUID renterId, Pageable pageable);

    @Query(value = "select r.* from hostel_request as r where r.due_time < ?1 ", nativeQuery = true)
    Collection<HostelRequest> findRequestsByLimitTime(Long limitTime);



}
