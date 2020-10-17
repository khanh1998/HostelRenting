package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Contract findByVendor_UserIdAndRenter_UserIdAndRoom_RoomId(Long vendorId, Long renterId, Integer roomId);
}
