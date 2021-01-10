package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.TypeFacility;
import org.avengers.capstone.hostelrenting.repository.TypeFacilityRepository;
import org.avengers.capstone.hostelrenting.service.TypeFacilityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeFacilitiesServiceImpl implements TypeFacilityService {
    private TypeFacilityRepository repository;
    private ModelMapper modelMapper;

    @Autowired
    public void setRepository(TypeFacilityRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TypeFacility create(TypeFacility input) {
        TypeFacility typeFacility = new TypeFacility();
        modelMapper.map(input, typeFacility);
        return repository.save(typeFacility);
    }

    @Override
    public void deleteById(List<Integer> indexes) {
        indexes.forEach(index -> {
            repository.deleteById(index);
        });
    }

    @Override
    public TypeFacility update(TypeFacility input) {
        Optional<TypeFacility> typeFacility = repository.findById(input.getId());
        if (typeFacility.isPresent()) {
            modelMapper.map(input, typeFacility);
            return repository.save(typeFacility.get());
        }
        throw new EntityNotFoundException(TypeFacility.class, "id", Integer.toString(input.getId()));
    }
}
