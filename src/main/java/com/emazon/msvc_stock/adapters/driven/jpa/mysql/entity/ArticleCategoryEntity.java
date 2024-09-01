package com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products_categories")
public class ProductCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
