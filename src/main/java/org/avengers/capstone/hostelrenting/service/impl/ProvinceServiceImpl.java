package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
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

    @Override
    public void checkExist(Integer provinceId) {
        Optional<Province> model = provinceRepository.findById(provinceId);
        if (model.isEmpty())
            throw new EntityNotFoundException(Province.class, "id", provinceId.toString());
    }

    @Override
    public Province create(Province reqModel) {
        Optional<Province> exModel = provinceRepository.getByProvinceName(reqModel.getProvinceName());
        if (exModel.isPresent())
            throw new GenericException(Province.class, "is duplicated",
                    "name", exModel.get().getProvinceName(),
                    "id", String.valueOf(exModel.get().getProvinceId()));

        return provinceRepository.save(reqModel);
    }


    @Override
    public List<Province> getAll() {
        return provinceRepository.findAll();
    }


    @Override
    public Province findById(Integer id) {
        checkExist(id);

        return provinceRepository.getOne(id);
    }
}
