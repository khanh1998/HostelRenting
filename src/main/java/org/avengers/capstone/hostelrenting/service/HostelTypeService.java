package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.HostelType;

import java.util.List;

public interface HostelTypeService {
    HostelType findById(Integer id);
    List<HostelType> findAll();
    HostelType save(HostelType hostelType);
    void delete(Integer id);

    HostelType findByIdAndHostelGroupId(Integer hostelTypeId, Integer hostelGroupId);
    List<HostelType> findByHostelGroupId(Integer hostelGroupId);
}
