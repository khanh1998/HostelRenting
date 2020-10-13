package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
}
