package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOFull;
import org.avengers.capstone.hostelrenting.model.Province;

import java.util.List;

public interface ProvinceService {
    /**
     * Check that given id is existed or not
     *
     * @param id the given id
     */
    void checkNotFound(Integer id);

    /**
     * Create new if not present, otherwise update
     *
     * @param reqDTO request dto
     * @return created model
     */
    ProvinceDTOFull save(ProvinceDTOFull reqDTO);

    /**
     * Find province by given id
     *
     * @param id the given id
     * @return province model
     */
    Province findById(Integer id);

    /**
     * Get all provinces
     *
     * @return list of DTOs
     */
    List<ProvinceDTOFull> getAll();




}
