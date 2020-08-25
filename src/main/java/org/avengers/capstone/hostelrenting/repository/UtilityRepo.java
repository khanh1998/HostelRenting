package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Utility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityRepo extends JpaRepository<Utility, Integer> {
}
