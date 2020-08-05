package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.FacilityDTO;
import org.avengers.capstone.hostelrenting.model.Facility;

import java.util.List;

public interface FacilityService {
    /**
     * Check that given id is existed or not
     *
     * @param id the given id
     */
    void checkExist(Integer id);

    /**
     * Get all facilities
     *
     * @return list of DTOs
     */
    List<FacilityDTO> getAll();

    /**
     * Find facility by given id
     *
     * @param id the given id
     * @return facility model
     */
    Facility findById(Integer id);
}
