package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    /**
     * Get Province object by provinceName - to check duplicating
     * @param provinceName
     * @return Province
     */
    Optional<Province> getByProvinceName(String provinceName);
}
