package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.HostelType;

import java.util.List;
import java.util.Optional;

public interface HostelTypeService {
    List<HostelType> findAllHostelType();
    Optional<HostelType> findHostelTypeById(Integer id);
    HostelType findHostelTypeByHostelTypeId(Integer hostelTypeId);
    List<HostelType> findAllHostelTypeByHostelGroupId(Integer hostelGroupId);
    HostelType saveHostelType(HostelType hostelType);
    void removeHostelType(HostelType hostelType);
}
