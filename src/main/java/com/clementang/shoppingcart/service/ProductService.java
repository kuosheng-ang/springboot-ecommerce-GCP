package com.clementang.shoppingcart.service;

import com.clementang.shoppingcart.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ProductService {
    void saveProduct(Product product);

    void saveProducts(Set<Product> products);

    List<Product> getAllProducts();

    void delete(int id);

    Product findByProductId (UUID id);

    Product findByProductNum(String Name);

    /*Page<Product> findAllProductsPageable(Pageable pageable);*/
    Page<Product> findProductsPaginated(int pageNo, int pageSize, String sortField, String sortDirection);


    /*Product updateProduct(Product productDto);*/



}
