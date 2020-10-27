package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<Contract> findByVendor_UserIdAndRenter_UserIdAndRoom_RoomId(Long vendorId, Long renterId, Integer roomId);
    Optional<Contract> findFirstByRenter_UserIdAndRoom_Type_TypeIdAndStatusOrStatusOrderByCreatedAt(Long renterId, Integer contractId, Contract.STATUS status1, Contract.STATUS status2);
}
