package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    /**
     * Get Province object by provinceName - to check duplicating
     * @param provinceName
     * @return Province
     */
    Province getByProvinceName(String provinceName);
}
