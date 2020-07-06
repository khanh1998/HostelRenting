package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.CategoryDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Category;
import org.avengers.capstone.hostelrenting.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    private CategoryService categoryService;
    private ModelMapper modelMapper;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiSuccess> getAllCategories() {
        List<Category> results = categoryService.findAllCategory()
                .stream()
                .map(categories -> modelMapper.map(categories, Category.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no category"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess( results, String.format(GET_SUCCESS, "Category")));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ApiSuccess> getCategoryById(@PathVariable Integer categoryId) throws EntityNotFoundException {
        Category category = categoryService.findCategoryById(categoryId);
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(categoryDTO, String.format(GET_SUCCESS, "Category")));
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiSuccess> creatCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws DuplicateKeyException {

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category createdCategory = categoryService.saveCategory(category);
        categoryDTO = modelMapper.map(createdCategory, CategoryDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(categoryDTO, String.format(CREATE_SUCCESS, "Category")));
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<ApiSuccess> updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryDTO categoryDTO) throws EntityNotFoundException {
        Category oldCategory = modelMapper.map(categoryDTO, Category.class);
        oldCategory.setCategoryId(categoryId);
        CategoryDTO updatedCategory = modelMapper.map(categoryService.saveCategory(oldCategory), CategoryDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedCategory, String.format(UPDATE_SUCCESS, "Category")));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiSuccess> deleteCategory(@PathVariable Integer categoryId) throws EntityNotFoundException{
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
