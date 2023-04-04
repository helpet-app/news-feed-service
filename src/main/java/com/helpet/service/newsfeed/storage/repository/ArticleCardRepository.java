package com.helpet.service.newsfeed.storage.repository;

import com.helpet.service.newsfeed.storage.model.ArticleCard;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ArticleCardRepository extends JpaRepository<ArticleCard, UUID> {
    Optional<ArticleCard> findArticleCardById(UUID articleCardId);

    @Query(value = "SELECT * FROM find_all_articles_by_filter(:tagIds)",
           countQuery = "SELECT COUNT(*) FROM find_all_articles_by_filter(:tagIds)",
           nativeQuery = true)
    Page<UUID> findAllArticleIdsByFilter(UUID[] tagIds, Pageable pageable);

    @EntityGraph(attributePaths = {
            "createdBy",
            "tags"
    })
    List<ArticleCard> findAllWithManagerByIdInOrderByCreatedAtDesc(Collection<UUID> ids);

    default Page<ArticleCard> findAllByFilter(Set<UUID> tagIds, Pageable pageable) {
        Page<UUID> articleIds = findAllArticleIdsByFilter(Objects.nonNull(tagIds) ? tagIds.toArray(UUID[]::new) : null,
                                                          PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        return new PageImpl<>(findAllWithManagerByIdInOrderByCreatedAtDesc(articleIds.getContent()),
                              pageable,
                              articleIds.getTotalElements());
    }

    @Query(value = "SELECT * FROM find_all_content_manager_articles_by_filter(:contentManagerId, :tagIds)",
           countQuery = "SELECT COUNT(*) FROM find_all_content_manager_articles_by_filter(:contentManagerId, :tagIds)",
           nativeQuery = true)
    Page<UUID> findAllContentManagerArticleIdsByFilter(UUID contentManagerId, UUID[] tagIds, Pageable pageable);

    @EntityGraph(attributePaths = {
            "tags"
    })
    List<ArticleCard> findAllByIdInOrderByCreatedAtDesc(Collection<UUID> ids);

    default Page<ArticleCard> findAllContentManagerArticlesByFilter(UUID contentManagerId, Collection<UUID> tagIds, Pageable pageable) {
        Page<UUID> articleIds = findAllContentManagerArticleIdsByFilter(contentManagerId,
                                                                        Objects.nonNull(tagIds) ? tagIds.toArray(UUID[]::new) : null,
                                                                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        return new PageImpl<>(findAllByIdInOrderByCreatedAtDesc(articleIds.getContent()),
                              pageable,
                              articleIds.getTotalElements());
    }

    void deleteArticleCardByCreatedByIdAndId(UUID contentManagerId, UUID articleId);

    @EntityGraph(attributePaths = {
            "tags"
    })
    Optional<ArticleCard> findArticleCardByCreatedByIdAndId(UUID contentManagerId, UUID articleId);
}
