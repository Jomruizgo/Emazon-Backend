package com.emazon.msvc_stock.domain.api.usecase;

import com.emazon.msvc_stock.domain.api.IArticleServicePort;
import com.emazon.msvc_stock.domain.model.Article;
import com.emazon.msvc_stock.domain.model.Brand;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.spi.IArticlePersistencePort;
import com.emazon.msvc_stock.domain.spi.IBrandPersistencePort;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.msvc_stock.domain.util.Constants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticleUseCase implements IArticleServicePort {
    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort,
                          ICategoryPersistencePort categoryPersistencePort,
                          IBrandPersistencePort brandPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveArticle(Article article) {
        validateCategories(article.getCategories());
        article.setCategories(getValidCategories(article.getCategories()));

        article.setBrand(getValidBrand(article.getBrand()));

        articlePersistencePort.saveArticle(article);
    }

    @Override
    public List<Article> listArticles(String sortBy, String order, int page, int size) {
        String validSortBy = getValidSortByField(sortBy);
        boolean ascending = isAscendingOrder(order);

        return articlePersistencePort.findArticlesSortedByField(validSortBy, ascending, page, size);
    }

    private void validateCategories(Set<Category> categories) {
        if (categories.isEmpty() || categories.size() > Constants.MAX_CATEGORY_ASSOCIATED_TO_ARTICLE) {
            throw new IllegalArgumentException(Constants.LIMIT_CATEGORIES_TO_ARTICLE_MESSAGE);
        }
    }

    private Set<Category> getValidCategories(Set<Category> categories) {
        Set<Category> validCategories = new HashSet<>();

        for (Category category : categories) {
            Category validCategory = getValidCategory(category);
            validCategories.add(validCategory);
        }
        return validCategories;
    }

    private Category getValidCategory(Category category) {
        if (category.getId() != null) {
            return findCategoryById(category.getId());
        } else if (category.getName() != null) {
            return findCategoryByName(category.getName());
        } else {
            throw new IllegalArgumentException(Constants.CATEGORY_ID_OR_NAME_MANDATORY_MESSAGE);
        }
    }

    private Category findCategoryById(Long id) {
        Category existingCategory = categoryPersistencePort.findCategoryById(id);
        if (existingCategory == null) {
            throw new IllegalArgumentException(Constants.CATEGORY_ID_DOES_NOT_EXIST_MESSAGE + id);
        }
        return existingCategory;
    }

    private Category findCategoryByName(String name) {
        Category existingCategory = categoryPersistencePort.findCategoryByName(name);
        if (existingCategory == null) {
            throw new IllegalArgumentException(Constants.CATEGORY_NAME_DOES_NOT_EXIST_MESSAGE + name);
        }
        return existingCategory;
    }

    private Brand getValidBrand(Brand brand) {
        if (brand.getId() == null) {
            throw new IllegalArgumentException(Constants.INVALID_BRAND_MESSAGE);
        }

        Brand existingBrand = brandPersistencePort.findBrandById(brand.getId());
        if (existingBrand == null) {
            throw new IllegalArgumentException(Constants.INVALID_BRAND_MESSAGE);
        }

        return existingBrand;
    }

    private String getValidSortByField(String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) {
            return Constants.DEFAULT_ARTICLE_SORT_BY;
        }
        String validSortBy = Constants.SORT_ARTICLE_FIELDS.get(sortBy.toLowerCase());
        if (validSortBy == null) {
            throw new IllegalArgumentException(Constants.INVALID_SORT_ARTICLE_FIELD_MESSAGE + sortBy);
        }
        return validSortBy;
    }

    private boolean isAscendingOrder(String order) {
        if (order == null || order.isEmpty()) {
            return Constants.DEFAULT_ARTICLE_SORTING_ORDER.equalsIgnoreCase(Constants.DEFAULT_ARTICLE_SORTING_ORDER);
        }
        return Constants.DEFAULT_ARTICLE_SORTING_ORDER.equalsIgnoreCase(order);
    }
}
