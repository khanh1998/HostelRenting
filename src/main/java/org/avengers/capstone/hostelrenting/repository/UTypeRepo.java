package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.UType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UTypeRepo extends JpaRepository<UType, Integer> {
}
