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
@Table(name = "products")
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



    public Product() { }

    public Product(String description, String inStockNumber, String productImage, int inStockQuantity) {

        this.description = description;
        this.inStockNumber = inStockNumber;
        this.productImage = productImage;
        this.inStockQuantity = inStockQuantity;
    }


    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return description;
    }

    public void setName(String name) {
        this.description = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public String getInStockNumber() {
        return inStockNumber;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return this.description;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getCategoryId() {
        return this.categoryId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setInStockNumber(String stockNumber){

        this.inStockNumber = stockNumber;
    }

    public int getInStockQuantity() {
        return this.inStockQuantity;
    }

    public void setInStockQuantity(int AvailStockQuantity) {

        this.inStockQuantity = AvailStockQuantity;
    }

    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String image) {
         productImage = image;
    }

    public void setNamedTitle(String title) {

        this.namedTitle = title;
    }

    public String getNamedTitle() {

        return this.namedTitle;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String formattedSlug) {

         this.slug=formattedSlug;
    }

    public void setCreationDate(LocalDateTime createdAtDate) {

         this.createdAt =createdAtDate;
    }


}
