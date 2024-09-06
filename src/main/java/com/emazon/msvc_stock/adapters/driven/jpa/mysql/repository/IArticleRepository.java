package com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {

    @Query("SELECT a FROM ArticleEntity a " +
            "LEFT JOIN FETCH a.articleCategories ac " +
            "LEFT JOIN FETCH ac.category c " +
            "LEFT JOIN FETCH a.brand b")
    Page<ArticleEntity> findAllSorted(Pageable pageable);
}
