package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.TypeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeImageRepository extends JpaRepository<TypeImage, Integer> {
    TypeImage findByImageIdAndType_TypeId(Integer imageId, Integer typeId);
}
