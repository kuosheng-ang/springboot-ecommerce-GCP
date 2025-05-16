package com.clementang.shoppingcart.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//@Builder
@Entity
@Data
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(name = "inStockNumber", updatable = true, nullable = false)
    public String inStockNumber;

    //@Size(min = 10, max=1000, message = "Product Name or tile cannot be less than 10 & more than 100 characters long") // Bean property 'namedTitle' is not readable or has an invalid getter method
    public String namedTitle;

    //@Size(min = 10, max=15000, message = "Description must be at least 10 characters long")
    public String description;


    @Column(name = "price", nullable = false)
    public Double price;


    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "inStockQuantity", updatable = true, nullable = false)
    public int inStockQuantity;

    @JoinColumn(name="category_id")
    public UUID categoryId;

    private String productImage;


    @Column(name = "slug")
    private String slug;

    @ManyToMany
    @JoinTable(name = "products_orders",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "order_id", referencedColumnName = "id"))
    private Collection<Order> orders = new ArrayList<>();


    
}
