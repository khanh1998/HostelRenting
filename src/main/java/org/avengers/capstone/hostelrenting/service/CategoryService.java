package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Category;
import org.avengers.capstone.hostelrenting.model.District;

import java.util.List;

public interface CategoryService {
    Category findCategoryById(Integer id);
    List<Category> findAllCategory();
    Category saveCategory(Category category);
    void deleteCategory(Integer id);
}
