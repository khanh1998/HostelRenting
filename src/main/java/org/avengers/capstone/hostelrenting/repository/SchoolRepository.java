package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
}
