package org.avengers.capstone.hostelrenting.repository;

import capstone.tdhostel.model.HostelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelTypeRepository extends JpaRepository<HostelType, Integer> {
    @Query("SELECT h from HostelType h where h.hostelGroup in (SELECT v from HostelGroup v where v.hostelGroupId = ?1)")
    List<HostelType> findAllHostelTypeByHostelGroupId(Integer hostelGroupId);
}
