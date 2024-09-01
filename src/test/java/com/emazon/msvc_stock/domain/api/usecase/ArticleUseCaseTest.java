package com.emazon.msvc_stock.domain.api.usecase;

import com.emazon.msvc_stock.domain.model.Article;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.spi.IArticlePersistencePort;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.msvc_stock.domain.util.DomainConstantsTrial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    private Article article;
    private Set<Category> categories;

    @BeforeEach
    void setUp() {
        categories = new HashSet<>();
        categories.add(new Category(1L, "Electronics", "Various electronic items"));
        article = new Article(1L, "Smartphone", "Latest model", 10, new BigDecimal("699.99"), categories);
    }

    @Test
    void testSaveArticle_WhenCategoriesIsNull_ShouldThrowException() {
        article.setCategories(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            articleUseCase.saveArticle(article);
        });
        assertEquals(DomainConstantsTrial.LIMIT_CATEGORIES_TO_ARTICLE_MESSAGE, exception.getMessage());
    }

    @Test
    void testSaveArticle_WhenCategoriesIsEmpty_ShouldThrowException() {
        article.setCategories(new HashSet<>());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            articleUseCase.saveArticle(article);
        });
        assertEquals(DomainConstantsTrial.LIMIT_CATEGORIES_TO_ARTICLE_MESSAGE, exception.getMessage());
    }

    @Test
    void testSaveArticle_WhenCategoriesExceedLimit_ShouldThrowException() {
        // Simulando más categorías de las permitidas
        for (int i = 0; i < DomainConstantsTrial.MAX_CATEGORY_ASSOCIATED_TO_ARTICLE + 1; i++) {
            categories.add(new Category((long) i, "Category" + i, "Description"));
        }
        article.setCategories(categories);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            articleUseCase.saveArticle(article);
        });
        assertEquals(DomainConstantsTrial.LIMIT_CATEGORIES_TO_ARTICLE_MESSAGE, exception.getMessage());
    }

    @Test
    void testSaveArticle_WhenCategoryNameIsNull_ShouldThrowException() {
        categories.add(new Category(2L, null, "No name category"));
        article.setCategories(categories);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            articleUseCase.saveArticle(article);
        });
        assertEquals(DomainConstantsTrial.CATEGORY_NAME_MANDATORY_MESSAGE, exception.getMessage());
    }

    @Test
    void testSaveArticle_WhenCategoryNameDoesNotExist_ShouldThrowException() {
        String nonExistentCategoryName = "NonExistentCategory";
        categories.add(new Category(2L, nonExistentCategoryName, "Description"));
        article.setCategories(categories);

        when(categoryPersistencePort.findCategoryByName(nonExistentCategoryName)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            articleUseCase.saveArticle(article);
        });
        assertEquals(DomainConstantsTrial.CATEGORY_NAME_DOES_NOT_EXIST_MESSAGE + nonExistentCategoryName, exception.getMessage());
    }

    @Test
    void testSaveArticle_WhenCategoriesAreValid_ShouldSaveArticle() {
        // Simulando que las categorías existen en la base de datos
        when(categoryPersistencePort.findCategoryByName(anyString())).thenReturn(categories.iterator().next());

        // Verifica que el artículo se guarda correctamente cuando todas las categorías son válidas
        assertDoesNotThrow(() -> articleUseCase.saveArticle(article));
        verify(articlePersistencePort, times(1)).saveArticle(article);
    }
}

