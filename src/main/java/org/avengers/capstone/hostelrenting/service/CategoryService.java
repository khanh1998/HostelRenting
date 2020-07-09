package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category save(Category province);
    Category findById(Integer id);
    List<Category> findAll();
    void delete(Integer id);
}
