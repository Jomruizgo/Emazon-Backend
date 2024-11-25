package com.emazon.msvc_stock.domain.api.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.emazon.msvc_stock.domain.model.Article;
import com.emazon.msvc_stock.domain.model.Brand;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.spi.IArticlePersistencePort;
import com.emazon.msvc_stock.domain.spi.IBrandPersistencePort;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.msvc_stock.domain.util.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ArticleUseCaseTest {
    private IArticlePersistencePort articlePersistencePort;
    private ICategoryPersistencePort categoryPersistencePort;
    private IBrandPersistencePort brandPersistencePort;
    private ArticleUseCase articleUseCase;

    @BeforeEach
    void setUp() {
        articlePersistencePort = mock(IArticlePersistencePort.class);
        categoryPersistencePort = mock(ICategoryPersistencePort.class);
        brandPersistencePort = mock(IBrandPersistencePort.class);
        articleUseCase = new ArticleUseCase(articlePersistencePort, categoryPersistencePort, brandPersistencePort);
    }

    @Test
    void testSaveArticleWithValidData() {
        Article article = new Article();
        article.setCategories(getMockCategories());
        article.setBrand(new Brand(1L, "BrandName", "Description"));

        when(categoryPersistencePort.findCategoryById(anyLong())).thenReturn(new Category(1L, "Electronics", "Description"));
        when(brandPersistencePort.findBrandById(anyLong())).thenReturn(new Brand(1L, "BrandName", "Description"));

        articleUseCase.saveArticle(article);

        verify(articlePersistencePort, times(1)).saveArticle(article);
    }

    @Test
    void increaseArticleStocks_shouldReturnFalse_whenQuantityIsLessThanOrEqualToZero() {
        boolean result = articleUseCase.increaseArticleStocks(1L, 0);
        assertFalse(result);

        result = articleUseCase.increaseArticleStocks(1L, -5);
        assertFalse(result);

        verifyNoInteractions(articlePersistencePort);
    }

    @Test
    void increaseArticleStocks_shouldReturnFalse_whenArticleNotFound() {
        when(articlePersistencePort.findArticleByArticleId(1L)).thenReturn(null);

        boolean result = articleUseCase.increaseArticleStocks(1L, 10);
        assertFalse(result);

        verify(articlePersistencePort).findArticleByArticleId(1L);
        verifyNoMoreInteractions(articlePersistencePort);
    }

    @Test
    void increaseArticleStocks_shouldReturnTrue_whenArticleFoundAndQuantityIsPositive() {
        Article mockArticle = mock(Article.class);
        when(articlePersistencePort.findArticleByArticleId(1L)).thenReturn(mockArticle);
        when(mockArticle.getQuantity()).thenReturn(5);

        boolean result = articleUseCase.increaseArticleStocks(1L, 10);

        assertTrue(result);
        verify(articlePersistencePort).findArticleByArticleId(1L);
        verify(mockArticle).setQuantity(15); // 5 + 10
        verify(articlePersistencePort).saveArticle(mockArticle);
    }

    @Test
    void increaseArticleStocks_shouldReturnFalse_whenExceptionOccurs() {
        Article mockArticle = mock(Article.class);
        when(articlePersistencePort.findArticleByArticleId(1L)).thenReturn(mockArticle);
        when(mockArticle.getQuantity()).thenReturn(5);
        doThrow(new RuntimeException()).when(articlePersistencePort).saveArticle(mockArticle);

        boolean result = articleUseCase.increaseArticleStocks(1L, 10);

        assertFalse(result);
        verify(articlePersistencePort).findArticleByArticleId(1L);
        verify(mockArticle).setQuantity(15); // 5 + 10
        verify(articlePersistencePort).saveArticle(mockArticle);
    }

    @Test
    void testSaveArticleWithInvalidCategory() {
        Article article = new Article();
        Set<Category> categories = new HashSet<>();
        categories.add(new Category(null, null, "Description"));  // Invalid category
        article.setCategories(categories);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> articleUseCase.saveArticle(article));
        assertEquals(TestConstants.CATEGORY_ID_OR_NAME_MANDATORY_MESSAGE, exception.getMessage());
    }

    @Test
    void testSaveArticleWithInvalidBrand() {
        Article article = new Article(
                1L,
                "Laptop",
                "High-end laptop",
                10,
                new BigDecimal("1200.00"),
                new Brand(null, "InvalidBrand", "Description"), // Marca inválida con ID nulo
                getMockCategories() // Categorías válidas
        );

        // Simular la validación de categorías
        when(categoryPersistencePort.findCategoryById(anyLong()))
                .thenReturn(new Category(1L, "Electronics", "Description"));

        // Ejecutar el test y verificar que se lanza la excepción correcta
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> articleUseCase.saveArticle(article));
        assertEquals(TestConstants.INVALID_BRAND_MESSAGE, exception.getMessage());
    }

    @Test
    void testListArticlesWithValidParams() {
        when(articlePersistencePort.findArticlesSortedByField(anyString(), anyBoolean(), anyInt(), anyInt()))
                .thenReturn(List.of(new Article(), new Article()));

        List<Article> articles = articleUseCase.listArticles("name", "asc", 0, 10);

        assertNotNull(articles);
        assertEquals(2, articles.size());
    }

    @Test
    void findCategoryByName_shouldReturnCategory_whenCategoryExists() {
        Category mockCategory = new Category();
        when(categoryPersistencePort.findCategoryByName("Electronics")).thenReturn(mockCategory);

        Category result = categoryPersistencePort.findCategoryByName("Electronics");

        assertNotNull(result);
        assertEquals(mockCategory, result);
        verify(categoryPersistencePort).findCategoryByName("Electronics");
    }


    @Test
    void testListArticlesWithInvalidSortField() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> articleUseCase.listArticles("invalidField", "asc", 0, 10));

        assertEquals(TestConstants.INVALID_SORT_ARTICLE_FIELD_MESSAGE + "invalidField", exception.getMessage());
    }

    private Set<Category> getMockCategories() {
        Set<Category> categories = new HashSet<>();
        categories.add(new Category(1L, "Electronics", "Description"));
        categories.add(new Category(2L, "Appliances", "Description"));
        return categories;
    }
}


