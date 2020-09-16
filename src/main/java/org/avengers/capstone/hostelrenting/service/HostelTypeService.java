package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.HostelType;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface HostelTypeService {
    HostelType findById(Integer id);

    HostelType save(HostelType hostelType);

    void deleteById(Integer id);

    List<HostelType> findByHostelGroupId(Integer hostelGroupId);

    /**
     * Get hostel types based on input factors
     * In case all factor are present, descending priority factors are: long, lat, distance; school; hometown
     *
     * @param latitude of the input location
     * @param longitude of the input location
     * @param distance radius from the input location
     * @param sortBy attribute to sort the result
     * @param asc true for ascending order
     * @param size size of a result
     * @param page page of the result
     * @return corresponding hostel types with factors
     */
    Collection<HostelType> searchWithMainFactors(Double latitude, Double longitude, Double distance, Integer schoolId, Integer districtId, String sortBy, Boolean asc, int size, int page);
}
