package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.StatisticDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Street;
import org.avengers.capstone.hostelrenting.repository.StatisticRepository;
import org.avengers.capstone.hostelrenting.repository.StreetRepository;
import org.avengers.capstone.hostelrenting.service.StatisticService;
import org.avengers.capstone.hostelrenting.service.StreetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticRepository repository;
    @Autowired
    private StreetRepository streetRepository;
//    @Autowired
//    private StreetService streetService;
    private ModelMapper modelMapper;

//    @Autowired
//    public void setRepository(StatisticRepository repository) {
//        this.repository = repository;
//    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    @Autowired
//    public void setStreetService(StreetService streetService) {
//        this.streetService = streetService;
//    }

    @Override
    public List<StatisticDTO> getStatisticByStreetWardIds(Integer[] ids) {
//        Arrays.stream(ids).forEach(id -> streetService.checkExist(id));
        for(Integer id : ids){
            if (!streetRepository.existsById(id))
                throw new EntityNotFoundException(Street.class, "id", id.toString());
        }
        String idStr = "";
        for (int i = 0; i < ids.length; i++) {
            idStr += ids[i];
            if (i < ids.length - 1) {
                idStr += ",";
            }
        }

        return repository.findByStreetWardId(idStr)
                .stream()
                .map(s -> modelMapper.map(s, StatisticDTO.class))
                .collect(Collectors.toList());
    }

}
