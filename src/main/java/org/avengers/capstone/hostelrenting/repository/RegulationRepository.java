package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Regulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author duattt on 10/6/20
 * @created 06/10/2020 - 13:05
 * @project youthhostelapp
 */
@Repository
public interface RegulationRepository extends JpaRepository<Regulation, Integer> {
    Optional<Regulation> findByRegulationName(String regulationName);
}
