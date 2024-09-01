package com.emazon.msvc_stock.domain.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Article {
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
    private Set<Category> categories= new HashSet<>();

    public Article() {}

    public Article(Long id, String name, String description, int quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setCategories(Set<Category> categories) { this.categories = categories; }
    public Set<Category> getCategories() { return categories; }
}
