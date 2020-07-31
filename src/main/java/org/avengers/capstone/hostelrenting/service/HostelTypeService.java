package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.HostelType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HostelTypeService {
    HostelType findById(Integer id);

    HostelType save(HostelType hostelType);

    void deleteById(Integer id);

    List<HostelType> findByHostelGroupId(Integer hostelGroupId);

    List<HostelType> findByLocationAndDistance(Double latitude, Double longitude, Double distance, String sortBy, Boolean asc, int size, int page);
}
