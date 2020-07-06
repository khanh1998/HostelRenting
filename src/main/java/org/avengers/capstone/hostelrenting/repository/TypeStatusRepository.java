package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.TypeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeStatusRepository extends JpaRepository<TypeStatus, Integer> {
    TypeStatus getByStatusName(String statusName);
}
