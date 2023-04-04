package com.helpet.service.newsfeed.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.newsfeed.dto.request.CreateArticleRequest;
import com.helpet.service.newsfeed.dto.request.UpdateArticleRequest;
import com.helpet.service.newsfeed.mapper.ContentManagerArticleCardMapper;
import com.helpet.service.newsfeed.mapper.ContentManagerArticleMapper;
import com.helpet.service.newsfeed.service.ArticleService;
import com.helpet.service.newsfeed.storage.model.Article;
import com.helpet.service.newsfeed.storage.model.ArticleCard;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RolesAllowed(Role.CONTENT_MANAGER)
@RequestMapping("/content-manager/articles")
@RestController
public class ContentManagerArticleController {
    private final ArticleService articleService;

    private final ContentManagerArticleCardMapper articleCardMapper;

    private final ContentManagerArticleMapper articleMapper;

    @Autowired
    public ContentManagerArticleController(ArticleService articleService,
                                           ContentManagerArticleCardMapper articleCardMapper,
                                           ContentManagerArticleMapper articleMapper) {
        this.articleService = articleService;
        this.articleCardMapper = articleCardMapper;
        this.articleMapper = articleMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getArticleCards(@RequestParam(value = "tag-id", required = false) Set<UUID> tagIds,
                                                        Pageable pageable,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID contentManagerId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Page<ArticleCard> articleCards = articleService.getContentManagerArticleCards(contentManagerId, tagIds, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(articleCardMapper.mapPage(articleCards));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<ResponseBody> getArticle(@PathVariable("article-id") UUID articleId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID contentManagerId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Article article = articleService.getContentManagerArticle(contentManagerId, articleId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(articleMapper.map(article));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createArticle(@RequestBody @Valid CreateArticleRequest createArticleRequest,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID contentManagerId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Article article = articleService.createArticle(contentManagerId, createArticleRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(articleMapper.map(article));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{article-id}")
    public ResponseEntity<ResponseBody> updateArticle(@PathVariable("article-id") UUID articleId,
                                                      @RequestBody @Valid UpdateArticleRequest updateArticleRequest,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID contentManagerId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Article article = articleService.updateArticle(contentManagerId, articleId, updateArticleRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(articleMapper.map(article));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{article-id}")
    public ResponseEntity<ResponseBody> deleteArticle(@PathVariable("article-id") UUID articleId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID contentManagerId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        articleService.deleteArticle(contentManagerId, articleId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}