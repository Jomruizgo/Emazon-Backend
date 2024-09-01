package com.emazon.msvc_stock.adapters.driving.http.controller;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msvc_stock.adapters.driving.http.mapper.request.IArticleRequestMapper;
import com.emazon.msvc_stock.domain.api.IArticleServicePort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleRestControllerAdapter {
    private final IArticleServicePort articleServicePort;
    private final IArticleRequestMapper articleRequestMapper;

    public ArticleRestControllerAdapter(IArticleServicePort articleServicePort, IArticleRequestMapper articleRequestMapper) {
        this.articleServicePort = articleServicePort;
        this.articleRequestMapper = articleRequestMapper;
    }
    @PostMapping("/")
    public ResponseEntity<Void> addArticle(@Valid @RequestBody AddArticleRequest request){
        articleServicePort.saveArticle(articleRequestMapper.addRecuestToArticle(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
