package com.helpet.service.newsfeed.storage.repository;

import com.helpet.service.newsfeed.storage.model.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    @EntityGraph(attributePaths = {
            "articleCard.createdBy",
            "articleCard.tags"
    })
    Optional<Article> findArticleById(UUID id);

    @EntityGraph(attributePaths = {
            "articleCard.tags"
    })
    Optional<Article> findArticleByArticleCardCreatedByIdAndId(UUID contentManagerId, UUID articleId);
}