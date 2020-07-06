package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelTypeRepository extends JpaRepository<HostelType, Integer> {
    @Query("SELECT h from HostelType h where h.hostelGroup.hostelGroupId in (SELECT v.hostelGroupId from HostelGroup v where v.hostelGroupId = ?1)")
    List<HostelType> findAllHostelTypeByHostelGroupId(Integer hostelGroupId);
}
