package com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "article_name", nullable = false)
    private String name;
    @Column(name= "article_description", nullable = false)
    private String description;
    @Column(name= "quantity", nullable = false)
    private Integer quantity;
    @Column(name= "price", nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "article",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ArticleCategoryEntity> articleCategories = new HashSet<>();
    //Using Set in order to avoid duplicates categories associated with an article

    @ManyToOne
    @JoinColumn( nullable = false, name = "brand_id")
    private BrandEntity brand;

}
