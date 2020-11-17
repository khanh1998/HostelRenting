package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 21:08
 * @project youthhostelapp
 */
@Repository
public interface HostelRequestRepository extends JpaRepository<HostelRequest, Integer> {
}
