package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Integer> {
}
