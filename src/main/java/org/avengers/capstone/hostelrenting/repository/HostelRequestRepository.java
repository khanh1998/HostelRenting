package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 21:08
 * @project youthhostelapp
 */
@Repository
public interface HostelRequestRepository extends JpaRepository<HostelRequest, Integer> {
    Collection<HostelRequest> findByRenter_UserIdAndDueTimeIsGreaterThan(Long renterId, long dueDate);

    @Query(value = "select r.* from hostel_request as r where to_timestamp(r.due_time/ 1000) < current_date", nativeQuery = true)
    Collection<HostelRequest> findExpiredRequests();

}
