package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.FacilityDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Facility;
import org.avengers.capstone.hostelrenting.repository.FacilityRepository;
import org.avengers.capstone.hostelrenting.service.FacilityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacilityServiceImpl implements FacilityService {
    private FacilityRepository facilityRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setFacilityRepository(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    /**
     * Check that given id is existed or not
     *
     * @param id the given id
     */
    @Override
    public void checkExist(Integer id) {
        Optional<Facility> model = facilityRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Facility.class, "id", id.toString());
    }

    /**
     * Get all facilities
     *
     * @return list of DTOs
     */
    @Override
    public List<FacilityDTO> getAll() {
        return facilityRepository.findAll()
                .stream()
                .map(facility -> modelMapper.map(facility, FacilityDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Find facility by given id
     *
     * @param id the given id
     * @return facility model
     */
    @Override
    public Facility findById(Integer id) {
        checkExist(id);

        return facilityRepository.getOne(id);
    }
}
