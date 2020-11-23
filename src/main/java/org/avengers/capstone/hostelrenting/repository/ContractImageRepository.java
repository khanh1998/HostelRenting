package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.ContractImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author duattt on 23/11/2020
 * @created 23/11/2020 - 10:41
 * @project youthhostelapp
 */
@Repository
public interface ContractImageRepository extends JpaRepository<ContractImage, Integer> {
    Optional<ContractImage> findByResourceUrl(String resourceUrl);
}
