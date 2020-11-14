package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.model.GroupService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author duattt on 10/16/20
 * @created 16/10/2020 - 15:09
 * @project youthhostelapp
 */
@Repository
public interface GroupServiceRepository extends JpaRepository<GroupService, Integer> {
    Collection<GroupService> findByGroup_GroupIdAndIsActiveIsTrue(Integer groupId);
    Collection<GroupService> findByGroup_GroupIdAndIsActiveIsTrueAndIsRequiredIsTrueAndGroupServiceIdNotIn(Integer groupId, Collection<Integer> reqServiceIds);
    @Query("select case when count (gs) > 0 then true else false end " +
            "from GroupService gs " +
            "where gs.group.vendor.userId= :vendorId and gs.group.groupId= :groupId and gs.groupServiceId= :groupServiceId")
    boolean IsGroupServiceExistByVendorAndGroup(Long vendorId, int groupId, int groupServiceId);

    Collection<GroupService> findByGroup_GroupIdAndService_IsApproved(Integer groupId, boolean isApproved);
}
