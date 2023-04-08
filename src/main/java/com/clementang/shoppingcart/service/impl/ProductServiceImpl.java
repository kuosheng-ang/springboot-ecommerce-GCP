package com.clementang.shoppingcart.service.impl;

import com.clementang.shoppingcart.model.*;
import com.clementang.shoppingcart.repository.*;
import com.clementang.shoppingcart.service.*;
import com.clementang.shoppingcart.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Future;

import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasLength;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(Product product) {
         productRepository.save(product);
    }

    public void saveProducts(Set<Product> products) {
        productRepository.save(products);
    }

    @Override
    public List<Product> getAllProducts() {
        return  productRepository.findAll();
    }

    public Product findByProductId(UUID id) { return productRepository.findById(id); }

    public Product findByProductNum(String name){
        return productRepository.findBySlug(name);
    };

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    /*@Override
    public Page<Product> findAllProductsPageable(Pageable pageable) {
        return productRepository.findAll(pageable);
    }*/
    public Page<Product> findProductsPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.productRepository.findAll(pageable);
    }


    /*public Product createProduct(Product productDto) {
        return productRepository.save(Product.builder()
                .inStockNumber(productDto.getInStockNumber())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build());
    }

    public Product updateProduct(Product productDto) {
        return productRepository
                .findById(productDto.getId())
                .map(existingProduct -> {
                            modifyUpdatedFields(productDto, existingProduct);
                            return productRepository.save(existingProduct);
                        }
                ).orElseThrow(() -> new ProductNotExistsException("Product doesn't exist in the system to update"));
    }*/

    private void modifyUpdatedFields(Product productDto, Product existingProduct) {
        if (hasLength(productDto.getName())) {
            existingProduct.setName(productDto.getName());
        }
        if (hasLength(productDto.getDescription())) {
            existingProduct.setDescription(productDto.getDescription());
        }
        if (nonNull(productDto.getPrice())) {
            existingProduct.setPrice(productDto.getPrice());
        }
    }



}
