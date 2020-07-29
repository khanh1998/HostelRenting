package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.TypeService;

import java.util.List;

public interface HostelTypeServiceService {
    List<TypeService> findByHostelTypeId(Integer id);
    List<TypeService> save(List<TypeService> typeServices);

}
