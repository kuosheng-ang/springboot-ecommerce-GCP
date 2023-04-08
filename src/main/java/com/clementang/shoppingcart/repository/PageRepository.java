package com.clementang.shoppingcart.repository;

import java.util.List;

import com.clementang.shoppingcart.model.Page;


import com.clementang.shoppingcart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Integer> {
    
    Page findBySlug(String slug);
    Page findBySlugAndIdNot(String slug, int id);
    List<Page> findAllByOrderBySortingAsc();


}
