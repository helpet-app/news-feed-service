package com.helpet.service.newsfeed.controller;

import com.helpet.service.newsfeed.mapper.ArticleCardMapper;
import com.helpet.service.newsfeed.mapper.ArticleMapper;
import com.helpet.service.newsfeed.service.ArticleService;
import com.helpet.service.newsfeed.storage.model.Article;
import com.helpet.service.newsfeed.storage.model.ArticleCard;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RequestMapping("/articles")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    private final ArticleCardMapper articleCardMapper;

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleController(ArticleService articleService, ArticleCardMapper articleCardMapper, ArticleMapper articleMapper) {
        this.articleService = articleService;
        this.articleCardMapper = articleCardMapper;
        this.articleMapper = articleMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getArticleCardsByFilter(@RequestParam(value = "tag-id", required = false) Set<UUID> tagIds,
                                                                Pageable pageable) {
        Page<ArticleCard> articleCards = articleService.getArticleCardsByFilter(tagIds, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(articleCardMapper.mapPage(articleCards));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<ResponseBody> getArticle(@PathVariable("article-id") UUID articleId) {
        Article article = articleService.getArticle(articleId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(articleMapper.map(article));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
