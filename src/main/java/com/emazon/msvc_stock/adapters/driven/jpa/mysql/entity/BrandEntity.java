package com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity;

import com.emazon.msvc_stock.domain.util.DomainConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name= "brand_name",unique = true, nullable = false, length = DomainConstants.MAX_BRAND_NAME_LENGTH)
    private String name;
    @Column(name= "brand_description", nullable = false, length = DomainConstants.MAX_BRAND_DESCRIPTION_LENGTH)
    private String description;
}
