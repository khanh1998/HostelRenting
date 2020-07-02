package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.repository.ProvinceRepository;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    private ProvinceRepository provinceRepository;

    @Autowired
    public void setProvinceRepository(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public Province save(Province province) {
        return provinceRepository.save(province);
    }


    public Province getProvinceNoException(Integer id){
        return provinceRepository.getOne(id);
    }
    @Override
    public Province findById(Integer id) {
        Province province = provinceRepository.getOne(id);
        if (province == null){
            throw new EntityNotFoundException(Province.class, "id", id.toString());
        }
        return province;
    }

    @Override
    public List<Province> findAll() {
        return provinceRepository.findAll().stream().filter(province -> !province.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        provinceRepository.deleteById(id);
    }

    @Override
    public long getCount() {
        return provinceRepository.count();
    }
}
