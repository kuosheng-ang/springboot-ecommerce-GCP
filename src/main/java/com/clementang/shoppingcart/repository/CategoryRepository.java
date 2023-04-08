package com.clementang.shoppingcart.repository;

import java.util.List;
import java.util.UUID;

import com.clementang.shoppingcart.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
    List<Category> findAllByOrderBySortingAsc();
	Category findBySlug(String slug);
    Category findById(UUID categoryId);


}
