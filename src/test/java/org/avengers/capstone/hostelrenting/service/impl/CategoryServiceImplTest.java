package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.CategoryDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Category;
import org.avengers.capstone.hostelrenting.repository.CategoryRepository;
import org.avengers.capstone.hostelrenting.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("dev")
@Tag("Service")
class CategoryServiceImplTest {
    /**
     * Autowired the service need to test
     */
    @Autowired
    private CategoryService service;

    @MockBean
    private CategoryRepository mockRepository;

    @Test
    @DisplayName("Test getAll")
    void testGetAll(){
        // Set up mock repository
        Category category1 = Mockito.mock(Category.class);
        Category category2 = Mockito.mock(Category.class);
        when(mockRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Execute the service call
        List<CategoryDTO> categories = service.getAll();

        // Assert the response
        Assertions.assertEquals(2, categories.size());
    }

    @Test
    @DisplayName("Test check existed not found")
    void testCheckExistNotFound(){
        //Set up mock repository
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Execute the service call
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> service.checkExist(5));

        // Assert the response
        Assertions.assertTrue(exception != null);
    }

    @Test
    @DisplayName("Test check existed success")
    void testCheckExist(){
        //Set up mock repository
        Category category = Mockito.mock(Category.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(category));

        // Assert the response
        Assertions.assertDoesNotThrow(() -> service.checkExist(5));
    }
}