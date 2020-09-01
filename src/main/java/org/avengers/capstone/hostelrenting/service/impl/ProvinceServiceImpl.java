package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.repository.ProvinceRepository;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
     * @param reqModel request model
     * @return response model
     */
    @Override
    public Province save(Province reqModel) {

        return provinceRepository.save(reqModel);
    }


    /**
     * Get all provinces
     *
     * @return list of models
     */
    @Override
    public List<Province> getAll() {
        return provinceRepository.findAll();
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
