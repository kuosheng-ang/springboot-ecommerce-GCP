package com.clementang.shoppingcart.service.impl;

import com.clementang.shoppingcart.model.Product;
import com.clementang.shoppingcart.repository.*;
import com.clementang.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.clementang.shoppingcart.model.*;
import com.clementang.shoppingcart.repository.*;
import com.clementang.shoppingcart.service.*;
import com.clementang.shoppingcart.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;





@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return  categoryRepository.findAll();
    }
    public Category findCategoryById(UUID id) { return categoryRepository.findById(id); }

    public Category findByCategory(String category) { return categoryRepository.findBySlug(category); }

    public void deleteCategoryById(int id) {
        categoryRepository.deleteById(id);
    }

    /*public Page<Category> findAllCategoriesPageable(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }*/


    public Page<Category> findCategoriesPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.categoryRepository.findAll(pageable);
    }


}