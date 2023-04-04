package com.helpet.service.newsfeed.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.newsfeed.dto.request.AddArticleTagsRequest;
import com.helpet.service.newsfeed.dto.request.CreateTagRequest;
import com.helpet.service.newsfeed.dto.request.DeleteArticleTagsRequest;
import com.helpet.service.newsfeed.storage.model.ArticleCard;
import com.helpet.service.newsfeed.storage.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleTagService {
    private final ArticleService articleService;

    private final TagService tagService;

    @Autowired
    public ArticleTagService(ArticleService articleService, TagService tagService) {
        this.articleService = articleService;
        this.tagService = tagService;
    }

    public Set<Tag> getArticleTags(UUID contentManagerId, UUID articleId) throws NotFoundLocalizedException {
        ArticleCard articleCard = articleService.getContentManagerArticleCard(contentManagerId, articleId);
        return articleCard.getTags();
    }

    public Set<Tag> addArticleTags(UUID contentManagerId, UUID articleId, AddArticleTagsRequest articleTagsInfo) throws NotFoundLocalizedException {
        ArticleCard articleCard = articleService.getContentManagerArticleCard(contentManagerId, articleId);

        Set<CreateTagRequest> newTagsRequests = articleTagsInfo.getTags()
                                                               .stream()
                                                               .map(tag -> CreateTagRequest.builder().name(tag).build())
                                                               .collect(Collectors.toSet());

        List<Tag> tags = tagService.createTagsIfNeeded(newTagsRequests);

        articleCard.getTags().addAll(tags);
        articleService.saveArticleCard(articleCard);

        return articleCard.getTags();
    }

    public void deleteArticleTags(UUID contentManagerId, UUID articleId, DeleteArticleTagsRequest articleTagsInfo) throws NotFoundLocalizedException {
        ArticleCard articleCard = articleService.getContentManagerArticleCard(contentManagerId, articleId);
        List<Tag> articleTags = tagService.getTagsInNames(articleTagsInfo.getTags());
        articleTags.forEach(articleCard.getTags()::remove);
        articleService.saveArticleCard(articleCard);
    }
}
