package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HostelTypeRepository extends JpaRepository<HostelType, Integer> {
    Optional<HostelType> findByTypeIdAndHostelGroup_GroupId(Integer hosteltypeId, Integer hostelGroupId);

    List<HostelType> findByHostelGroup_GroupId(Integer hostelGroupId);

    @Query(value = "SELECT t.type_id, t.capacity, t.price, t.superficiality, t.type_name, t.category_id, t.group_id, t.status_id, t.is_deleted " +
            "FROM public.province as p, public.district as d, public.ward as w, public.hostel_type as t, public.hostel_group as g " +
            "WHERE (d.province_id = p.province_id and w.district_id = d.district_id and g.ward_id = w.ward_id and t.group_id = g.group_id) and  " +
            "(w.ward_name LIKE ?1 or d.district_name LIKE ?1 or p.province_name LIKE ?1) " +
            "GROUP BY t.type_id",
            nativeQuery = true)
    List<HostelType> findByAddress(String address);
}
