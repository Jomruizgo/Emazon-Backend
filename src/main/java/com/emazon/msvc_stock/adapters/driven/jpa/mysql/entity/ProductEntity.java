package com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity;

import com.emazon.msvc_stock.domain.util.DomainConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name= "product_name", nullable = false)
    private String name;
    @Column(name= "product_description", nullable = false)
    private String description;
    @Column(name= "quantity", nullable = false)
    private Integer quantity;
    @Column(name= "price", nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductCategoryEntity> productCategories = new HashSet<>();
}
