package com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.emazon.msvc_stock.domain.model.Article;
import com.emazon.msvc_stock.domain.spi.IArticlePersistencePort;

public class ArticleAdapter implements IArticlePersistencePort {
    private final IArticleRepository articleRepository;
    private final IArticleEntityMapper articleEntityMapper;

    public ArticleAdapter(IArticleRepository articleRepository, IArticleEntityMapper articleEntityMapper) {
        this.articleRepository = articleRepository;
        this.articleEntityMapper = articleEntityMapper;
    }

    @Override
    public void saveArticle(Article article) {
        articleRepository.save(articleEntityMapper.toEntity(article));
    }
}
