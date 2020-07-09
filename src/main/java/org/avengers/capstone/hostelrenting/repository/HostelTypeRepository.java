package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HostelTypeRepository extends JpaRepository<HostelType, Integer> {
    Optional<HostelType> findByTypeIdAndHostelGroup_GroupId(Integer hosteltypeId, Integer hostelGroupId);
    List<HostelType> findByHostelGroup_GroupId(Integer hostelGroupId);
}
