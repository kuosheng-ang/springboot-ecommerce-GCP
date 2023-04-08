package com.clementang.shoppingcart.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private UUID id;

    @Size(min = 2, message = "Name must be at least 2 characters long")
    @Column(name = "name", nullable = false, updatable = true)
    private String name;


    private String slug;

    private int sorting;

    public String getCategoryName() {
        return name;
    }

    public void setCategoryName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setSorting(int sort) {
        this.sorting = sort;
    }

    public UUID getId() { return this.id; }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public int getSorting() {
        return sorting;
    }
}
