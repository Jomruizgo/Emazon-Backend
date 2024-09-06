package com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.emazon.msvc_stock.domain.model.Article;
import com.emazon.msvc_stock.domain.spi.IArticlePersistencePort;
import com.emazon.msvc_stock.domain.util.DomainConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.List;

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

    @Override
    public List<Article> findArticlesSortedByField(String sortBy, boolean ascending, int page, int size) {

        Sort sort;

        if (DomainConstants.SORT_ARTICLE_FIELDS.get("category").equals(sortBy)) {
            sort = JpaSort.unsafe(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        } else {
            sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return articleEntityMapper.toModelList(articleRepository.findAllSorted(pageRequest).getContent());
    }
}
