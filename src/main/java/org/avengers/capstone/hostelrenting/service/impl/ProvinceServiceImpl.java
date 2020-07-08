package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.repository.ProvinceRepository;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        if (provinceRepository.getByProvinceName(province.getProvinceName()) != null) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "province_name", province.getProvinceName()));
        }
        return provinceRepository.save(province);
    }

    @Override
    public Province findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Province.class, "id", id.toString());
        }
            return provinceRepository.getOne(id);
    }

    @Override
    public List<Province> findAll() {
        return provinceRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        if (isNotFound(id)) {
            throw new EntityNotFoundException(Province.class, "id", id.toString());
        }
        provinceRepository.deleteById(id);

    }

    private boolean isNotFound(Integer id) {
        Optional<Province> province = provinceRepository.findById(id);
        return province.isEmpty();
    }

    public long getCount() {
        return provinceRepository.count();
    }
}
