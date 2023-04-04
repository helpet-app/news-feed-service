package com.helpet.service.newsfeed.storage.repository;

import com.helpet.service.newsfeed.storage.model.AccountFavorite;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountFavoriteRepository extends JpaRepository<AccountFavorite, AccountFavorite.Id> {
    @Query(value = "SELECT * FROM find_favorite_articles(:accountId)",
           countQuery = "SELECT COUNT(*) FROM find_favorite_articles(:accountId)",
           nativeQuery = true)
    Page<UUID> findAllFavoriteArticlesIds(UUID accountId, Pageable pageable);

    @EntityGraph(attributePaths = {
            "articleCard.createdBy",
            "articleCard.tags"
    })
    List<AccountFavorite> findAllByIdArticleIdInOrderByAddedAtDesc(Collection<UUID> articleIds);

    default Page<AccountFavorite> findAllFavoriteArticles(UUID accountId, Pageable pageable) {
        Page<UUID> articleIds = findAllFavoriteArticlesIds(accountId, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        return new PageImpl<>(findAllByIdArticleIdInOrderByAddedAtDesc(articleIds.getContent()),
                              pageable,
                              articleIds.getTotalElements());
    }
}
