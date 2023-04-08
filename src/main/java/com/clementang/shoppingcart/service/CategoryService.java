package com.clementang.shoppingcart.service;

import com.clementang.shoppingcart.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {



    List<Category> getAllCategories();

    void deleteCategoryById(int id);

    Category findCategoryById(UUID id);

    Category findByCategory(String category);

    //Page<Category> findAllCategoriesPageable(Pageable pageable);
    Page<Category> findCategoriesPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    void saveCategory(Category category);



}
