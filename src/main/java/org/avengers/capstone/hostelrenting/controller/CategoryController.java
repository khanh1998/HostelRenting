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

    @PostMapping("/categories")
    public ResponseEntity<ApiSuccess> create(@Valid @RequestBody CategoryDTO rqDTO) throws DuplicateKeyException {
        Category rqModel = modelMapper.map(rqDTO, Category.class);
        Category responseModel = categoryService.save(rqModel);
        CategoryDTO responseDTO = modelMapper.map(responseModel, CategoryDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(responseDTO, String.format(CREATE_SUCCESS, Category.class.getSimpleName())));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiSuccess> getAll(@RequestParam (required = false) String categoryName,
                                             @RequestParam(required = false, defaultValue = "50") Integer size,
                                             @RequestParam(required = false, defaultValue = "0") Integer page) {
        List<CategoryDTO> results = categoryService.findAll()
                .stream()
                .filter(category -> {
                    if (categoryName!= null)
                        return category.getCategoryName().toLowerCase().contains(categoryName.trim().toLowerCase());
                    return true;
                }).skip(page * size)
                .limit(size)
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no category"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess( results, String.format(GET_SUCCESS, Category.class.getSimpleName())));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ApiSuccess> getCategoryById(@PathVariable Integer categoryId) throws EntityNotFoundException {
        Category category = categoryService.findById(categoryId);
        CategoryDTO responseDTO = modelMapper.map(category, CategoryDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(responseDTO, String.format(GET_SUCCESS, Category.class.getSimpleName())));
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer categoryId, @RequestBody CategoryDTO categoryDTO) throws EntityNotFoundException {
        Category oldCategory = modelMapper.map(categoryDTO, Category.class);
        oldCategory.setCategoryId(categoryId);
        CategoryDTO updatedCategory = modelMapper.map(categoryService.save(oldCategory), CategoryDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(updatedCategory, String.format(UPDATE_SUCCESS, Category.class.getSimpleName())));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiSuccess> delete(@PathVariable Integer categoryId) throws EntityNotFoundException{
        categoryService.deleteById(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
