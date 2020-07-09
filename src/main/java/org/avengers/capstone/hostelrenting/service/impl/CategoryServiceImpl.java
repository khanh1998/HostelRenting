package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Category;
import org.avengers.capstone.hostelrenting.repository.CategoryRepository;
import org.avengers.capstone.hostelrenting.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        if (categoryRepository.getByCategoryName(category.getCategoryName()) != null){
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "category_name", category.getCategoryName()));
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Category.class, "id", id.toString());
        }
        return categoryRepository.getOne(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Category.class, "id", id.toString());
        }
        categoryRepository.deleteById(id);
    }

    private boolean isNotFound(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.isEmpty();
    }
}
