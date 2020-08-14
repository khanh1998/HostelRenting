package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Street;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetRepository extends JpaRepository<Street, Integer> {
}
