package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelTypeServiceRepository extends JpaRepository<TypeService, Integer> {
    List<TypeService> findByHostelType_TypeId(Integer id);
}
