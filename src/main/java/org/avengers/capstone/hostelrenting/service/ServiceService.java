package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.ServiceDTO;
import org.avengers.capstone.hostelrenting.model.Service;

import java.util.List;

public interface ServiceService {
    /**
     * Check that given id is existed or not
     *
     * @param id the given id
     */
    void checkNotFound(Integer id);

    /**
     * Get all services
     *
     * @return list of DTO
     */
    List<ServiceDTO> getAll();

    /**
     * Find service by given id
     *
     * @param id the given id
     * @return service model
     */
    Service findById(Integer id);
}
