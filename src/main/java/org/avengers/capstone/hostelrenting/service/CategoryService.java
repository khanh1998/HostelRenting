package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.category.CategoryDTO;
import org.avengers.capstone.hostelrenting.model.Category;

import java.util.List;

public interface CategoryService {
    /**
     * Check that given id is existed or not
     *
     * @param id the given id
     */
    void checkExist(Integer id);

    /**
     * Get all categories
     *
     * @return list of DTOs
     */
    List<CategoryDTO> getAll();

    Category findById(Integer id);
}
