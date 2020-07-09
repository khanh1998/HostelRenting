package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category getByCategoryName(String categoryName);
}
