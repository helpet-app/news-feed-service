package com.helpet.service.newsfeed.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.newsfeed.dto.request.CreateArticleRequest;
import com.helpet.service.newsfeed.dto.request.UpdateArticleRequest;
import com.helpet.service.newsfeed.service.error.NotFoundLocalizedError;
import com.helpet.service.newsfeed.storage.model.Article;
import com.helpet.service.newsfeed.storage.model.ArticleCard;
import com.helpet.service.newsfeed.storage.model.ContentManager;
import com.helpet.service.newsfeed.storage.repository.ArticleCardRepository;
import com.helpet.service.newsfeed.storage.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class ArticleService {
    private final ContentManagerProfileService contentManagerProfileService;

    private final ArticleCardRepository articleCardRepository;

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ContentManagerProfileService contentManagerProfileService,
                          ArticleCardRepository articleCardRepository,
                          ArticleRepository articleRepository) {
        this.contentManagerProfileService = contentManagerProfileService;
        this.articleCardRepository = articleCardRepository;
        this.articleRepository = articleRepository;
    }

    public boolean articleExists(UUID articleId) {
        return articleCardRepository.existsById(articleId);
    }

    public Page<ArticleCard> getArticleCardsByFilter(Set<UUID> tagIds, Pageable pageable) {
        return articleCardRepository.findAllByFilter(tagIds, pageable);
    }

    public Article getArticle(UUID id) throws NotFoundLocalizedException {
        return articleRepository.findArticleById(id)
                                .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.ARTICLE_DOES_NOT_EXIST));
    }

    public ArticleCard getArticleCard(UUID id) throws NotFoundLocalizedException {
        return articleCardRepository.findArticleCardById(id)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.ARTICLE_DOES_NOT_EXIST));
    }

    public Page<ArticleCard> getContentManagerArticleCards(UUID contentManagerId,
                                                           Set<UUID> tagIds,
                                                           Pageable pageable) throws NotFoundLocalizedException {
        if (!contentManagerProfileService.contentManagerExists(contentManagerId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.CONTENT_MANAGER_DOES_NOT_EXIST);
        }

        return articleCardRepository.findAllContentManagerArticlesByFilter(contentManagerId, tagIds, pageable);
    }

    public Article getContentManagerArticle(UUID contentManagerId, UUID articleId) throws NotFoundLocalizedException {
        if (!contentManagerProfileService.contentManagerExists(contentManagerId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.CONTENT_MANAGER_DOES_NOT_EXIST);
        }

        return articleRepository.findArticleByArticleCardCreatedByIdAndId(contentManagerId, articleId)
                                .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.CONTENT_MANAGER_DOES_NOT_HAVE_THIS_ARTICLE));
    }

    public ArticleCard getContentManagerArticleCard(UUID contentManagerId, UUID articleId) throws NotFoundLocalizedException {
        if (!contentManagerProfileService.contentManagerExists(contentManagerId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.CONTENT_MANAGER_DOES_NOT_EXIST);
        }

        return articleCardRepository.findArticleCardByCreatedByIdAndId(contentManagerId, articleId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.CONTENT_MANAGER_DOES_NOT_HAVE_THIS_ARTICLE));
    }

    public Article createArticle(UUID contentManagerId, CreateArticleRequest articleInfo) throws NotFoundLocalizedException {
        ContentManager contentManager = contentManagerProfileService.getProfile(contentManagerId);

        ArticleCard newArticleCard = ArticleCard.builder()
                                                .title(articleInfo.getTitle())
                                                .description(articleInfo.getDescription())
                                                .imageUrl(articleInfo.getImageUrl())
                                                .createdBy(contentManager)
                                                .build();

        Article newArticle = Article.builder()
                                    .articleCard(newArticleCard)
                                    .content(articleInfo.getContent())
                                    .sourceName(articleInfo.getSourceName())
                                    .sourceLink(articleInfo.getSourceLink())
                                    .build();

        return articleRepository.save(newArticle);
    }

    public Article updateArticle(UUID contentManagerId, UUID articleId, UpdateArticleRequest updateArticleRequest) throws NotFoundLocalizedException {
        Article article = getContentManagerArticle(contentManagerId, articleId);
        ArticleCard articleCard = article.getArticleCard();

        articleCard.setTitle(updateArticleRequest.getTitle());
        articleCard.setDescription(updateArticleRequest.getDescription());
        articleCard.setImageUrl(updateArticleRequest.getImageUrl());

        article.setContent(updateArticleRequest.getContent());
        article.setSourceName(updateArticleRequest.getSourceName());
        article.setSourceLink(updateArticleRequest.getSourceLink());

        return articleRepository.save(article);
    }

    @Transactional
    public void deleteArticle(UUID contentManagerId, UUID articleId) throws NotFoundLocalizedException {
        if (!contentManagerProfileService.contentManagerExists(contentManagerId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.CONTENT_MANAGER_DOES_NOT_EXIST);
        }

        articleCardRepository.deleteArticleCardByCreatedByIdAndId(contentManagerId, articleId);
    }

    public void saveArticleCard(ArticleCard articleCard) {
        articleCardRepository.save(articleCard);
    }
}
