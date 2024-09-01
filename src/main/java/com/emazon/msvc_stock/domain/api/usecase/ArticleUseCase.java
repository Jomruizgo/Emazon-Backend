package com.emazon.msvc_stock.domain.api.usecase;

import com.emazon.msvc_stock.domain.api.IArticleServicePort;
import com.emazon.msvc_stock.domain.model.Article;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.spi.IArticlePersistencePort;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.msvc_stock.domain.util.DomainConstants;

import java.util.HashSet;
import java.util.Set;

public class ArticleUseCase implements IArticleServicePort {
    public final IArticlePersistencePort articlePersistencePort;
    public final ICategoryPersistencePort categoryPersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveArticle(Article article) {
        if (article.getCategories() == null || article.getCategories().isEmpty() ||
                article.getCategories().size() > DomainConstants.MAX_CATEGORY_ASSOCIATED_TO_ARTICLE) {
            throw new IllegalArgumentException(DomainConstants.LIMIT_CATEGORIES_TO_ARTICLE_MESSAGE);
        }
        //Validamos y completamos los objetos Category, que antes de esta linea solo tienen el atributo name.
        article.setCategories(validateCategories(article.getCategories()));

        articlePersistencePort.saveArticle(article);
    }

    Set<Category> validateCategories(Set<Category> categories) {
        Set<Category> validCategories = new HashSet<>();
        for (Category category : categories) {
            if (category.getName() == null) {
                throw new IllegalArgumentException("Category must have a name");
            }

            Category existingCategory = categoryPersistencePort.findCategoryByName(category.getName());
            if (existingCategory == null) {
                throw new IllegalArgumentException("Category with name '" + category.getName() + "' does not exist");
            }

            validCategories.add(existingCategory);
        }
        return validCategories;
    }
}
