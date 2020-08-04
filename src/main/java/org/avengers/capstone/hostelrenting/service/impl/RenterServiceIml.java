package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOFull;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.RenterRepository;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RenterServiceIml implements RenterService {
    private RenterRepository renterRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRenterRepository(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<Renter> model = renterRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Renter.class, "id", id.toString());
    }

    @Override
    public User update(UserDTOFull reqDTO) {
        checkExist(reqDTO.getUserId());

        //Update firebase Token
        User exModel = renterRepository.getOne(reqDTO.getUserId());
        if (exModel.getFirebaseToken() == null
                || !exModel.getFirebaseToken().equals(reqDTO.getFirebaseToken())) {
            exModel.setFirebaseToken(reqDTO.getFirebaseToken());

            return renterRepository.save(modelMapper.map(exModel, Renter.class));
        }

        return modelMapper.map(exModel, Renter.class);
    }

    @Override
    public Renter findById(Integer id) {
        checkExist(id);
        return renterRepository.getOne(id);

    }

    @Override
    public List<Renter> findAll() {
        return renterRepository.findAll();
    }

    @Override
    public Renter save(Renter renter) {
        if (renterRepository.equals(renter)){
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "all", "all"));
        }

        return renterRepository.save(renter);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)){
            throw new EntityNotFoundException(Renter.class, "id", id.toString());
        }

        renterRepository.deleteById(id);
    }

    private boolean isNotFound(Integer id) {
        Optional<Renter> renter = renterRepository.findById(id);
        return renter.isEmpty();
    }
}
