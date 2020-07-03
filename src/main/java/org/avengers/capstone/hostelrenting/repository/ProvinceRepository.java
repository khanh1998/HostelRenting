package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
<<<<<<< HEAD
    
=======
    Province getByProvinceName(String provinceName);
>>>>>>> ddb5bbacfd5f10612ee0a65ca7efb42599328d52
}
