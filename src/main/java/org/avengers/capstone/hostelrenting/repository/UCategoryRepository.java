package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.UCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UCategoryRepository extends JpaRepository<UCategory, Integer> {

}
