package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.TypeImage;

import java.util.List;

public interface TypeImageService {
    TypeImage findById(Integer id);
    List<TypeImage> findAll();
    TypeImage save(TypeImage typeImage);
    void deleteById(Integer id);
    void deleteByIds(List<Integer> indexes);

    TypeImage findByIdAndHostelTypeId(Integer imageId, Integer typeId);
}
