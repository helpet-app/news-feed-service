package com.helpet.service.newsfeed.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.newsfeed.dto.request.AddArticleTagsRequest;
import com.helpet.service.newsfeed.dto.request.DeleteArticleTagsRequest;
import com.helpet.service.newsfeed.mapper.TagMapper;
import com.helpet.service.newsfeed.service.ArticleTagService;
import com.helpet.service.newsfeed.storage.model.Tag;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RolesAllowed(Role.CONTENT_MANAGER)
@RequestMapping("/content-manager/articles/{article-id}/tags")
@RestController
public class ContentManagerArticleTagController {
    private final ArticleTagService articleTagService;

    private final TagMapper tagMapper;

    @Autowired
    public ContentManagerArticleTagController(ArticleTagService articleTagService, TagMapper tagMapper) {
        this.articleTagService = articleTagService;
        this.tagMapper = tagMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getArticleTags(@PathVariable("article-id") UUID articleId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID contentManagerId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Set<Tag> articleTags = articleTagService.getArticleTags(contentManagerId, articleId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(tagMapper.mapCollection(articleTags));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> addArticleTags(@PathVariable("article-id") UUID articleId,
                                                       @RequestBody @Valid AddArticleTagsRequest addArticleTagsRequest,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID contentManagerId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Set<Tag> articleTags = articleTagService.addArticleTags(contentManagerId, articleId, addArticleTagsRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(tagMapper.mapCollection(articleTags));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ResponseBody> deleteArticleTags(@PathVariable("article-id") UUID articleId,
                                                         @RequestBody @Valid DeleteArticleTagsRequest deleteArticleTagsRequest,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID contentManagerId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        articleTagService.deleteArticleTags(contentManagerId, articleId, deleteArticleTagsRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}