package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.repository.ProvinceRepository;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProvinceServiceImpl implements ProvinceService {
    private ProvinceRepository provinceRepository;


    @Autowired
    public void setProvinceRepository(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    /**
     * save the province
     * @param province
     * @return
     * @throws DuplicateKeyException when province name is existed
     */
    @Override
    public Province save(Province province) throws DuplicateKeyException{
        if (provinceRepository.getByProvinceName(province.getProvinceName()) != null) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "province_name", province.getProvinceName()));
        }
        return provinceRepository.save(province);
    }

    /**
     * Get the province by given id
     * @param id
     * @return Province
     * @throws EntityNotFoundException when not found given id
     */
    @Override
    public Province findById(Integer id) throws  EntityNotFoundException{
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Province.class, "id", id.toString());
        }
            return provinceRepository.getOne(id);
    }

    /**
     * get all Provinces with page and size (default page=1, size=50)
     * @param page
     * @param size
     * @return List of provinces
     */
    @Override
    public List<Province> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return provinceRepository.findAll(pageable).toList();
    }

    /**
     * Check that given id existed or not
     * @param id
     * @return true if existed, otherwise false
     */
    private boolean isNotFound(Integer id) {
        Optional<Province> province = provinceRepository.findById(id);
        return province.isEmpty();
    }
}
