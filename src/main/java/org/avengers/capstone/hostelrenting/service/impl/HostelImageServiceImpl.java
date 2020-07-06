package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelImage;
import org.avengers.capstone.hostelrenting.repository.HostelImageRepository;
import org.avengers.capstone.hostelrenting.service.HostelImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HostelImageServiceImpl implements HostelImageService {
    HostelImageRepository hostelImageRepository;

    @Autowired
    public void setHostelImageRepository(HostelImageRepository hostelImageRepository) {
        this.hostelImageRepository = hostelImageRepository;
    }

    @Override
    public HostelImage findHostelImageById(Integer id) {
        Optional<HostelImage> hostelImage = hostelImageRepository.findById(id);
        if (hostelImage.isEmpty()){
            throw new EntityNotFoundException(HostelImage.class, "id", id.toString());
        }else{
            return hostelImage.get();
        }
    }

    @Override
    public List<HostelImage> findAllHostelImage() {
        return hostelImageRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public HostelImage saveHostelImage(HostelImage hostelImage) {
        return hostelImageRepository.save(hostelImage);
    }

    @Override
    public void deleteHostelImage(Integer id) {
        hostelImageRepository.deleteById(id);
    }
}
