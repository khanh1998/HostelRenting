package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.ProvinceDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.repository.ProvinceRepository;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProvinceServiceImpl implements ProvinceService {
    private ProvinceRepository provinceRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setProvinceRepository(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    /**
     * Check that given id is existed or not
     *
     * @param id the given id
     */
    @Override
    public void checkNotFound(Integer id) {
        Optional<Province> model = provinceRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Province.class, "id", id.toString());
    }

    /**
     * Create new if not present, otherwise update
     *
     * @param reqDTO request dto
     * @return response DTO
     */
    @Override
    public ProvinceDTO save(ProvinceDTO reqDTO) {
        Province model = modelMapper.map(reqDTO, Province.class);

        Province resModel = provinceRepository.save(model);

        return modelMapper.map(resModel, ProvinceDTO.class);
    }


    /**
     * Get all provinces
     *
     * @return list of DTOs
     */
    @Override
    public List<ProvinceDTO> getAll() {
        return provinceRepository.findAll()
                .stream()
                .map(province -> modelMapper.map(province, ProvinceDTO.class))
                .collect(Collectors.toList());
    }


    /**
     * Find province by given id
     *
     * @param id the given id
     * @return province model
     */
    @Override
    public Province findById(Integer id) {
        checkNotFound(id);

        return provinceRepository.getOne(id);
    }
}
