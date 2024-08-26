package com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity;

import com.emazon.msvc_stock.domain.util.DomainConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name= "category_name",unique = true, nullable = false, length = DomainConstants.MAX_CATEGORY_NAME_LENGTH)
    private String name;
    @Column(name= "category_description", nullable = false, length = DomainConstants.MAX_CATEGORY_DESCRIPTION_LENGTH)
    private String description;
}
