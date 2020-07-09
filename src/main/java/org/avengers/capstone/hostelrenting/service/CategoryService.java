package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category province);
    Category findById(Integer id);
    List<Category> findAll();
    void delete(Integer id);
}
