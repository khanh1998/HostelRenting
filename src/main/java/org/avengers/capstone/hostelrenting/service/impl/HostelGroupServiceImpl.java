package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.repository.HostelGroupRepository;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HostelGroupServiceImpl implements HostelGroupService {
    private HostelGroupRepository hostelGroupRepository;

    @Autowired
    public void setHostelGroupRepository(HostelGroupRepository hostelGroupRepository) {
        this.hostelGroupRepository = hostelGroupRepository;
    }

    @Override
    public HostelGroup findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(HostelGroup.class, "id", id.toString());
        }
        return hostelGroupRepository.getOne(id);
    }

    @Override
    public List<HostelGroup> findAll() {
        return hostelGroupRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public HostelGroup save(HostelGroup hostelGroup) {
        if (hostelGroupRepository.equals(hostelGroup)) {
            throw new DuplicateKeyException(String.format("Hostel group is dupplicated"));
        }
        return hostelGroupRepository.save(hostelGroup);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(HostelGroup.class, "id", id.toString());
        }
        hostelGroupRepository.deleteById(id);
    }

    /**
     * Find all hostelgroups in 5km around
     *
     * @param longitude
     * @param latitude
     * @return
     */
    @Override
    public List<HostelGroup> getSurroundings(Double latitude, Double longitude) {
        double long2 = Math.toRadians(longitude);
        double lat2 = Math.toRadians(latitude);
        List<HostelGroup> results = findAll()
                .stream()
                .filter(hostelGroup -> {
                    double dlong = Math.toRadians(hostelGroup.getLongitude()) - long2;
                    double dlat = Math.toRadians(hostelGroup.getLatitude()) - lat2;
                    double a = Math.pow(Math.sin(dlat / 2), 2)
                            + Math.cos(lat2) * Math.cos(hostelGroup.getLatitude())
                            * Math.pow(Math.sin(dlong / 2), 2);
                    double c = 2 * Math.asin(Math.sqrt(a));
                    if (c * Constant.EARTH_RADIUS <= Constant.DEFAULT_RANGE){
                        System.out.println("******************************" + c * Constant.EARTH_RADIUS);
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        return results;
    }

    private boolean isNotFound(Integer id) {
        Optional<HostelGroup> hostelGroup = hostelGroupRepository.findById(id);
        return hostelGroup.isEmpty();
    }
}
