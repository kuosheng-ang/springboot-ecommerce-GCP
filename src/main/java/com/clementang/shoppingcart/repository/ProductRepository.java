package com.clementang.shoppingcart.repository;


import java.util.Optional;


import com.clementang.shoppingcart.model.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Size;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Product findBySlug(@Param("inStockNumber") String slug);
	Product findProductByDescriptionAndInStockNumber(String namedTitle, String inStockNumber);

	Product findProductByInStockNumber(String inStockNumber);
	Product findById(UUID id);

	Product deleteById(UUID id);

	Product findBySlugAndIdNot(String slug, int id);

	Page<Product> findAll(Pageable pageable);

	List<Product> findAllByCategoryId(UUID catId, Pageable pageable);  //No property categoryId found for type Product!

	long countByCategoryId(UUID catId);

	void save(Set<Product> product);
	@Override
	Product save(Product product);

}