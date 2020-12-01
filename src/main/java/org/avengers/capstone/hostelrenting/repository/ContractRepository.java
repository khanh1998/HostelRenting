package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Contract;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<Contract> findByVendor_UserIdAndRenter_UserIdAndRoom_RoomIdAndStatus(Long vendorId, Long renterId, Integer roomId, Contract.STATUS status);
    Optional<Contract> findFirstByRenter_UserIdAndRoom_Type_TypeIdAndStatusOrRenter_UserIdAndRoom_Type_TypeIdAndStatusOrderByCreatedAt
            (Long renterId, Integer typeId, Contract.STATUS status1,Long renterId2, Integer typeId2, Contract.STATUS status2);
    List<Contract> findByVendor_UserId(Long vendorId, Pageable pageable);
    List<Contract> findByRenter_UserId(Long renterId, Pageable pageable);

    @Query(value = "SELECT c.* FROM contract as c\n" +
            "WHERE \n" +
            "(to_timestamp(c.created_at/1000) < current_date - interval '?1' day)\n" +
            "and c.status = 'INACTIVE';", nativeQuery = true)
    Collection<Contract> findExpiredInactiveContractByDayRange(String dayRange);
}
