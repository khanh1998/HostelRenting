package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.facility.FacilityDTO;
import org.avengers.capstone.hostelrenting.model.Facility;

import java.util.List;

public interface FacilityService {
    /**
     * Check that given id is existed or not
     *
     * @param id the given id
     */
    void checkNotFound(Integer id);

    Facility createNew(Facility facility);

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
