package org.avengers.capstone.hostelrenting.service.impl;

import lombok.NonNull;
import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdate;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.School;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.repository.RenterRepository;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.avengers.capstone.hostelrenting.service.SchoolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RenterServiceIml implements RenterService {
    private RenterRepository renterRepository;
    private ModelMapper modelMapper;
    private ProvinceService provinceService;
    private SchoolService schoolService;

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @Autowired
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRenterRepository(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    @Override
    public void checkExist(Long id) {
        Optional<Renter> model = renterRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Renter.class, "id", id.toString());
    }

    @Override
    public Renter updateInfo(Renter exModel, RenterDTOUpdate reqDTO) {
        if (reqDTO.getProvince() != null) {
            Province province = provinceService.findById(reqDTO.getProvince().getProvinceId());
            exModel.setProvince(province);
        }
        if (reqDTO.getSchool() != null) {
            School school = schoolService.findById(reqDTO.getSchool().getSchoolId());
            exModel.setSchool(school);
        }
        modelMapper.map(reqDTO, exModel);


        return renterRepository.save(exModel);
    }

    @Override
    public Renter findById(Long id) {
        checkExist(id);

        return renterRepository.getOne(id);
    }

    @Override
    public Collection<Renter> findByIds(Collection<Long> ids) {
        return renterRepository.findByUserIdIn(ids);
    }

    @Override
    public Renter create(Renter renter) {
        if (renter.getSchool()!=null){
            renter.setSchool(schoolService.findById(renter.getSchool().getSchoolId()));
        }
        if (renter.getProvince() != null){
            renter.setProvince(provinceService.findById(renter.getProvince().getProvinceId()));
        }

        return renterRepository.save(renter);
    }


}
