package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Category;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.repository.CategoryRepository;
import org.avengers.capstone.hostelrenting.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            throw new EntityNotFoundException(Category.class, "id", id.toString());
        }else{
            return category.get();
        }
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Category saveCategory(Category category) {
        if (categoryRepository.getByCategoryName(category.getCategoryName()) != null) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "category_name", category.getCategoryName()));
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
