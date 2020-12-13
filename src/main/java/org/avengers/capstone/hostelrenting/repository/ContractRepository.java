package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Contract;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<Contract> findByVendor_UserIdAndRenter_UserIdAndRoom_RoomIdAndStatus(UUID vendorId, UUID renterId, Integer roomId, Contract.STATUS status);
    Optional<Contract> findFirstByRenter_UserIdAndRoom_Type_TypeIdAndStatusOrRenter_UserIdAndRoom_Type_TypeIdAndStatusOrderByCreatedAt
            (UUID renterId, Integer typeId, Contract.STATUS status1,UUID renterId2, Integer typeId2, Contract.STATUS status2);
    List<Contract> findByVendor_UserId(Long vendorId, Pageable pageable);
    List<Contract> findByRenter_UserId(Long renterId, Pageable pageable);

    @Query(value = "SELECT c.* FROM contract as c\n" +
            "WHERE \n" +
            "(c.created_at < ?1)\n" +
            "and c.status = 'INACTIVE';", nativeQuery = true)
    Collection<Contract> findExpiredInactiveContractByDayRange(Long limitTime);
}
