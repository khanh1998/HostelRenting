package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Facility;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.model.TypeImage;
import org.avengers.capstone.hostelrenting.repository.TypeImageRepository;
import org.avengers.capstone.hostelrenting.service.TypeImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeImageServiceImp implements TypeImageService {
    private TypeImageRepository typeImageRepository;

    @Autowired
    public void setTypeImageRepository(TypeImageRepository typeImageRepository) {
        this.typeImageRepository = typeImageRepository;
    }

    @Override
    public TypeImage findById(Integer id) {
        if (isNotFound(id))
            throw new EntityNotFoundException(Province.class, "id", id.toString());

        return typeImageRepository.getOne(id);
    }

    @Override
    public List<TypeImage> findAll() {
        return typeImageRepository.findAll();
    }

    @Override
    public TypeImage save(TypeImage typeImage) {
        return typeImageRepository.save(typeImage);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Facility.class, "id", id.toString());
        }
        typeImageRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Integer> indexes) {
        indexes.forEach(index -> {
            TypeImage image = findById(index);
            image.setDeleted(true);
            typeImageRepository.save(image);
        });
    }

    @Override
    public TypeImage findByIdAndHostelTypeId(Integer imageId, Integer typeId) {
        return typeImageRepository.findByImageIdAndType_TypeId(imageId, typeId);
    }

    private boolean isNotFound(Integer id) {
        Optional<TypeImage> typeImage = typeImageRepository.findById(id);
        return typeImage.isEmpty();
    }
}
