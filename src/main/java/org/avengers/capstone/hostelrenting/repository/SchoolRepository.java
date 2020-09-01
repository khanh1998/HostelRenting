package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Integer> {
}
