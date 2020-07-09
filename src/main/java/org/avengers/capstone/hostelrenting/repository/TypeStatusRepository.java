package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.TypeStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TypeStatusRepository extends JpaRepository<TypeStatus, Integer> {
    TypeStatus getByTypeStatusName(String typeStatusName);
}
