package com.helpet.service.newsfeed.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.newsfeed.dto.request.AddArticleToFavoritesRequest;
import com.helpet.service.newsfeed.mapper.ArticleCardMapper;
import com.helpet.service.newsfeed.service.FavoriteArticleService;
import com.helpet.service.newsfeed.storage.model.ArticleCard;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/user/articles/favorites")
@RestController
public class FavoriteArticleController {
    private final FavoriteArticleService favoriteArticleService;

    private final ArticleCardMapper articleCardMapper;

    @Autowired
    public FavoriteArticleController(FavoriteArticleService favoriteArticleService, ArticleCardMapper articleCardMapper) {
        this.favoriteArticleService = favoriteArticleService;
        this.articleCardMapper = articleCardMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getFavoriteArticles(Pageable pageable, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Page<ArticleCard> favoriteArticles = favoriteArticleService.getFavoriteArticles(accountId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(articleCardMapper.mapPage(favoriteArticles));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> addArticleToFavorites(@RequestBody @Valid AddArticleToFavoritesRequest addArticleToFavoritesRequest,
                                                              JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        favoriteArticleService.addArticleToFavorites(accountId, addArticleToFavoritesRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @DeleteMapping("/{article-id}")
    public ResponseEntity<ResponseBody> deleteArticleFromFavorites(@PathVariable("article-id") UUID articleId,
                                                                   JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        favoriteArticleService.deleteArticleFromFavorites(accountId, articleId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
