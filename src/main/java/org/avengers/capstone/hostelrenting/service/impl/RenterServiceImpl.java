package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.RenterDTO;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.repository.RenterRepository;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class RenterServiceImpl implements RenterService {
    private RenterRepository renterRepository;
    private ModelMapper mapper;

    public Renter createRenter(RenterDTO dto) {
        Renter r = null;
        try {
            r = convertToEntity(dto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return renterRepository.save(r);
    }

    public List<Renter> getAll() {
        return renterRepository.findAll();
    }

    public Renter convertToEntity(RenterDTO dto) throws ParseException {
        Renter r = mapper.map(dto, Renter.class);
        return r;
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setRenterRepository(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }
}
